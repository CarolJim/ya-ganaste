package com.pagatodo.yaganaste.ui.preferuser.iteractors;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.Request;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CerrarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DesasociarDispositivoRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActualizarAvatarResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DesasociarDispositivoResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserIteractor;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;
import com.pagatodo.yaganaste.utils.BitmapDownload;
import com.pagatodo.yaganaste.utils.Recursos;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTUALIZAR_AVATAR;

/**
 * Created by Francisco Manzo on 08/06/2017.
 * Iteractor para gestionar los eventos de PreferUSerPresenter
 */

public class PreferUserIteractor implements IPreferUserIteractor, IRequestResult {

    PreferUserPresenter preferUserPresenter;
    private boolean logOutBefore;

    public PreferUserIteractor(PreferUserPresenter preferUserPresenter) {
        this.preferUserPresenter = preferUserPresenter;
    }

    /**
     * Se encarga de recibir el request de cualquier tipo en el Presenter, y reacciona con el
     * instanceof correspondiente
     *
     * @param request
     */
    @Override
    public void desasociarToIteracto(Request request) {

        /**
         * Proceso para Desasociar
         */
        if (request instanceof DesasociarDispositivoRequest) {
            if (!RequestHeaders.getTokensesion().isEmpty()) {
                logOutBefore = false;
                desasociaDispositivo((DesasociarDispositivoRequest) request);
            } else {
                logOutBefore = true;
                logout();
            }
        }


    }

    @Override
    public void getImagenURLiteractor(String mUserImage) {
        String urlEdit = procesarURLString(mUserImage);
        BitmapDownload bitmapDownload = new BitmapDownload(urlEdit, this);
        bitmapDownload.execute();
    }

    @Override
    public void sendToIteractorBitmap(Bitmap bitmap) {
        preferUserPresenter.sendImageBitmapPresenter(bitmap);
    }

    @Override
    public void sendIteractorActualizarAvatar(ActualizarAvatarRequest avatarRequest) {
        try {
            ApiAdtvo.actualizarAvatar(avatarRequest, this);
        } catch (OfflineException e) {
            // e.printStackTrace();
            preferUserPresenter.onFailPresenter();
        }
    }

    @Override
    public void showExceptionToIteractor(String mMesage) {
        preferUserPresenter.showExceptionToPresenter(mMesage);
    }

    private String procesarURLString(String mUserImage) {
        String[] urlSplit = mUserImage.split("_");
        String urlEdit = urlSplit[0] + "_M.png";
        return urlEdit;
    }

    /**
     * Metodo que sirve para enviar a cerrar session
     */
    public void logout() {
        try {
            ApiAdtvo.cerrarSesion(this);
        } catch (OfflineException e) {
            // e.printStackTrace();
            preferUserPresenter.showExceptionToPresenter(e.toString());
        }
    }

    /**
     * Inicia la pericion al ApiAdtvo para consumir el servicio
     *
     * @param request
     */
    public void desasociaDispositivo(DesasociarDispositivoRequest request) {
        try {
            ApiAdtvo.desasociarDispositivo(request, this);
        } catch (OfflineException e) {
            // e.printStackTrace();
            preferUserPresenter.showExceptionToPresenter(e.toString());
        }
    }

    /**
     * Manejo de casos de Success del servidor
     *
     * @param dataSourceResult
     */
    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        if (dataSourceResult.getData() instanceof CerrarSesionRequest) {
            Log.d("PreferUserIteractor", "DataSource Sucess Server Error CerrarSesion");
        }

        /**
         * Instancia de peticion exitosa de DesasociarDispositivoResponse
         */
        if (dataSourceResult.getData() instanceof DesasociarDispositivoResponse) {
            DesasociarDispositivoResponse response = (DesasociarDispositivoResponse) dataSourceResult.getData();

            if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
                //Log.d("PreferUserIteractor", "DataSource Sucess " + response.getMensaje());
                preferUserPresenter.sendSuccessPresenter(response.getMensaje());
                logout();
            } else {
                //Log.d("PreferUserIteractor", "DataSource Sucess with Error " + response.getMensaje());
                preferUserPresenter.sendErrorPresenter(response.getMensaje());
            }
        }

        /**
         * Instancia de peticion exitosa de ActualizarAvatarResponse
         */
        if (dataSourceResult.getData() instanceof ActualizarAvatarResponse) {
            ActualizarAvatarResponse response = (ActualizarAvatarResponse) dataSourceResult.getData();
            if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
                Log.d("ListaOpcionesIteractor", "DataSource Sucess " + response.getMensaje());

                String urlEdit = procesarURLString(response.getData().getImagenAvatarURL());
                SingletonUser.getInstance().getDataUser().getUsuario().setImagenAvatarURL(urlEdit);
                preferUserPresenter.sucessUpdateAvatar();
                // Linea para simular el error y comprobar el Duialog y el ShowProgress
                //listaOpcionesPresenter.sendErrorPresenter(response.getMensaje());
            } else {
                Log.d("ListaOpcionesIteractor", "DataSource Sucess with Error " + response.getMensaje());
                preferUserPresenter.sendErrorAvatarPresenter(response.getMensaje());
            }
        }
    }

    /**
     * Manejo de errores de conexion con el servidor
     *
     * @param error
     */
    @Override
    public void onFailed(DataSourceResult error) {
        // Log.d("PreferUserIteractor", "Error: " + error);
        if (error.getWebService().equals(ACTUALIZAR_AVATAR)) {
            preferUserPresenter.sendErrorServerAvatarToPresenter(error.getData().toString());
        } else {
            preferUserPresenter.sendErrorServerPresenter(error.getData().toString());
        }
    }
}
