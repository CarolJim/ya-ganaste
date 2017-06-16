package com.pagatodo.yaganaste.ui.preferuser.iteractors;

import android.graphics.Bitmap;
import android.util.Log;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.Request;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CambiarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CambiarEmailRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CerrarSesionRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DesasociarDispositivoRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActualizarAvatarResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CambiarContraseniaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CambiarEmailResponse;
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
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CAMBIAR_CONTRASENIA;

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

    /**
     * @param mUserImage
     */
    @Override
    public void getImagenURLToIteractor(String mUserImage) {
        String urlEdit = procesarURLString(mUserImage);
        BitmapDownload bitmapDownload = new BitmapDownload(urlEdit, this);
        bitmapDownload.execute();
    }

    @Override
    public void sendToIteractorBitmap(Bitmap bitmap) {
        preferUserPresenter.sendImageBitmapToPresenter(bitmap);
    }

    @Override
    public void sendIteractorActualizarAvatar(ActualizarAvatarRequest avatarRequest) {
        try {
            ApiAdtvo.actualizarAvatar(avatarRequest, this);
        } catch (OfflineException e) {
            // e.printStackTrace();
            preferUserPresenter.showExceptionAvatarToPresenter(e.toString());
        }
    }

    /**
     * Manejo de la excepcion de error en el BitmapDownload, usamos el showExceptionToPresenter generico
     * para mostrar el mensaje de error
     *
     * @param mMesage
     */
    @Override
    public void showExceptionBitmapDownloadToIteractor(String mMesage) {
        preferUserPresenter.showExceptionToPresenter(mMesage);
    }

    /**
     * Enviamos la peticion al servicio del Cambio de Contraseña
     *
     * @param request
     */
    @Override
    public void sendChangePassToIteractor(CambiarContraseniaRequest request) {
        try {
            ApiAdtvo.cambiarContrasenia(request, this);
        } catch (OfflineException e) {
            // e.printStackTrace();
            preferUserPresenter.showExceptionPassToPresenter(e.toString());
        }
    }

    /**
     * Envia el Request de cambio de correo al servicio
     *
     * @param request
     */
    @Override
    public void changeEmailToIteractor(CambiarEmailRequest request) {
        try {
            ApiAdtvo.cambiarEmail(request, this);
        } catch (OfflineException e) {
            // e.printStackTrace();
            preferUserPresenter.showExceptionToPresenter(e.toString());
        }
    }

    /**
     * Envia el Request de cambio de pass al servicio
     *
     * @param request
     */
    @Override
    public void changePassToIteractor(CambiarContraseniaRequest request) {
        try {
            ApiAdtvo.cambiarContrasenia(request, this);
        } catch (OfflineException e) {
            // e.printStackTrace();
            preferUserPresenter.showExceptionPassToPresenter(e.toString());
        }
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
         * Instancia de peticion exitosa de ActualizarAvatarResponse
         */
        if (dataSourceResult.getData() instanceof ActualizarAvatarResponse) {
            ActualizarAvatarResponse response = (ActualizarAvatarResponse) dataSourceResult.getData();
            if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
                Log.d("ListaOpcionesIteractor", "DataSource Sucess " + response.getMensaje());

                String urlEdit = procesarURLString(response.getData().getImagenAvatarURL());
                SingletonUser.getInstance().getDataUser().getUsuario().setImagenAvatarURL(urlEdit);
                preferUserPresenter.successGenericToPresenter(dataSourceResult);
                // Linea para simular el error y comprobar el Duialog y el ShowProgress
                //preferUserPresenter.errorGenericToPresenter(dataSourceResult);
            } else {
                Log.d("ListaOpcionesIteractor", "DataSource Sucess with Error " + response.getMensaje());
                preferUserPresenter.errorGenericToPresenter(dataSourceResult);
            }
        }

        /**
         * Instancia de peticion exitosa de CambiarEmailResponse
         */
        if (dataSourceResult.getData() instanceof CambiarEmailResponse) {
            CambiarEmailResponse response = (CambiarEmailResponse) dataSourceResult.getData();

            if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
                Log.d("PreferUserIteractor", "DataSource Sucess " + response.getMensaje());
                preferUserPresenter.successGenericToPresenter(dataSourceResult);
            } else {
                Log.d("PreferUserIteractor", "DataSource Sucess with Error " + response.getMensaje());
                preferUserPresenter.errorGenericToPresenter(dataSourceResult);
            }
        }

        /**
         * Instancia de peticion exitosa de CambiarContraseniaResponse
         */
        if (dataSourceResult.getData() instanceof CambiarContraseniaResponse) {
            CambiarContraseniaResponse response = (CambiarContraseniaResponse) dataSourceResult.getData();

            if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
                //Log.d("PreferUserIteractor", "DataSource Sucess " + response.getMensaje());
                preferUserPresenter.successGenericToPresenter(dataSourceResult);
            } else {
                //Log.d("PreferUserIteractor", "DataSource Sucess with Error " + response.getMensaje());
                preferUserPresenter.errorGenericToPresenter(dataSourceResult);
            }
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
        } else if (error.getWebService().equals(CAMBIAR_CONTRASENIA)) {
            preferUserPresenter.sendErrorServerPassToPresenter(error.getData().toString());
        } else {
            preferUserPresenter.sendErrorServerPresenter(error.getData().toString());
        }
    }

    /**
     * Metodos auxiliares
     */
    private String procesarURLString(String mUserImage) {
        String[] urlSplit = mUserImage.split("_");
        String urlEdit = urlSplit[0] + "_M.png";
        return urlEdit;
    }
}
