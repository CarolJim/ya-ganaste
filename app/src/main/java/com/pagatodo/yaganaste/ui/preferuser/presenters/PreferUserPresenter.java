package com.pagatodo.yaganaste.ui.preferuser.presenters;

import android.graphics.Bitmap;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CambiarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CambiarEmailRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DesasociarDispositivoRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActualizarAvatarResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CambiarContraseniaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CambiarEmailResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DesasociarDispositivoResponse;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._manager.GenericPresenterMain;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyEmailView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyPassView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferDesasociarView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserGeneric;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserIteractor;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserPresenter;
import com.pagatodo.yaganaste.ui.preferuser.iteractors.PreferUserIteractor;
import com.pagatodo.yaganaste.utils.camera.CameraManager;

/**
 * Created by Francisco Manzo on 08/06/2017.
 * Presenter general para la actividad PreferUserActivity
 * Para gestionar todos los eventos de los fragmentos
 */

public class PreferUserPresenter extends GenericPresenterMain<IPreferUserGeneric> implements IPreferUserPresenter {

    PreferUserActivity mView;
    IPreferUserIteractor iPreferUserIteractor;
    IPreferDesasociarView iPreferDesasociarView;
    IListaOpcionesView iListaOpcionesView;
    IMyEmailView iMyEmailView;
    IMyPassView iMyPassView;

    public PreferUserPresenter(PreferUserActivity mView) {
        // super(mView); Esta linea hace funcionar el PResenterGeneric
        this.mView = mView;

        iPreferUserIteractor = new PreferUserIteractor(this);
    }

    /**
     * Se encarga de hacer SET del View que interactura con el Presenter. Esto funciona porque recibimos
     * una Interfase IPreferUserGeneric, y a su vez si es instancia de alguna de sus herederas haemos
     * SET de la View correspondiente. Este proceso es un Cast del tipo Downcasting
     *
     * @param iPreferUserGeneric
     */
    @Override
    public void setIView(IPreferUserGeneric iPreferUserGeneric) {
        super.setIView(iPreferUserGeneric);

        // Set de  instancia de IListaOpcionesView
        if (iPreferUserGeneric instanceof IListaOpcionesView) {
            iListaOpcionesView = (IListaOpcionesView) iPreferUserGeneric;
        }

        // Set de instancia de IPreferDesasociarView
        if (iPreferUserGeneric instanceof IPreferDesasociarView) {
            this.iPreferDesasociarView = (IPreferDesasociarView) iPreferUserGeneric;
        }

        // Set de instancia de IMyEmailView
        if (iPreferUserGeneric instanceof IMyEmailView) {
            this.iMyEmailView = (IMyEmailView) iPreferUserGeneric;
        }

        // Set de instancia de IMyPassView
        if (iPreferUserGeneric instanceof IMyPassView) {
            this.iMyPassView = (IMyPassView) iPreferUserGeneric;
        }
    }

    /**
     * Se encarga de enviar la peticion al Iteractor para Desasociar. Msotramos el Loader mientras
     * tanto. Arma la peticion del Request para enviaro an Iteractor
     */
    @Override
    public void DesasociarToPresenter() {
        mView.showLoader(App.getContext().getResources().getString(R.string.user_change_desasociar));
        DesasociarDispositivoRequest desasociarRequest = new DesasociarDispositivoRequest();
        iPreferUserIteractor.desasociarToIteracto(desasociarRequest);
    }

    /**
     * Error en la conexion al servidor y procedimiento de Desasociar. Cerrarmos el Loader y enviamos
     * el control a la vista
     *
     * @param error
     */
    @Override
    public void sendErrorServerPresenter(String error) {
        mView.hideLoader();
        iPreferDesasociarView.sendErrorServerView(error);
    }

    /**
     * Peticion al Iteractor para el servicio que proporciona la foto del servidor
     *
     * @param mUserImage
     */
    @Override
    public void getImagenURLToPresenter(String mUserImage) {
        iPreferUserIteractor.getImagenURLToIteractor(mUserImage);
    }

    /**
     * Recibe el Bitmap procesado, listo para enviarse a la vista
     *
     * @param bitmap
     */
    @Override
    public void sendImageBitmapToPresenter(Bitmap bitmap) {
        iListaOpcionesView.sendImageBitmapToView(bitmap);
    }

    /**
     * Usamos la instancia del CameraManager para abrir la camara e iniciar el procedimiento
     *
     * @param i
     * @param cameraManager
     */
    @Override
    public void openMenuPhoto(int i, CameraManager cameraManager) {
        try {
            cameraManager.createPhoto(1);
        } catch (Exception e) {
            //Toast.makeText(App.getContext(), "Exception " + e, Toast.LENGTH_SHORT).show();
            iListaOpcionesView.showExceptionToView(e.toString());
        }
    }

    @Override
    public void sendPresenterActualizarAvatar(ActualizarAvatarRequest avatarRequest) {
        iPreferUserIteractor.sendIteractorActualizarAvatar(avatarRequest);
    }

    @Override
    public void showExceptionToPresenter(String mMesage) {
        iListaOpcionesView.showExceptionToView(mMesage);
    }

    @Override
    public void sendErrorServerAvatarToPresenter(String mMesage) {
        iListaOpcionesView.sendErrorAvatarToView(mMesage);
        // iMyPassView.sendErrorPassToView(mensaje);
    }

 /*   @Override
    public void sendChangePassToPresenter() {

        mView.showLoader("Procesando. Por favor, espere . . .");
        CambiarContraseniaRequest cambioPassRequest = new CambiarContraseniaRequest();
        iPreferUserIteractor.sendChangePassToIteractor(cambioPassRequest);
    }*/

    /**
     * Envia el cambio de correo al servicio
     *
     * @param mOld
     * @param mNew
     */
    @Override
    public void changeEmailToPresenter(String mOld, String mNew) {
        mView.showLoader(App.getContext().getResources().getString(R.string.user_change_email));
        CambiarEmailRequest cambiarEmailRequest = new CambiarEmailRequest();
        iPreferUserIteractor.changeEmailToIteractor(cambiarEmailRequest);
    }

    /**
     * Enviamos al Iteractor la peticion de cambiar el Pass, y armamos el Request. Ademas de mostrar
     * un Loader de carga
     *
     * @param mPassActual
     * @param mPassNueva
     */
    @Override
    public void changePassToPresenter(String mPassActual, String mPassNueva) {
        mView.showLoader(App.getContext().getResources().getString(R.string.user_change_password));
        CambiarContraseniaRequest cambiarContraseniaRequest = new CambiarContraseniaRequest();
        cambiarContraseniaRequest.setContrasenaActual(mPassActual);
        cambiarContraseniaRequest.setContrasenaNueva(mPassNueva);
        iPreferUserIteractor.changePassToIteractor(cambiarContraseniaRequest);
    }

    /**
     * Exito en la conexion al servidor y procedimiento de Desasociar. Cerrarmos el Loader y enviamos
     * el control a la vista
     *
     * @param dataSourceResult
     */
    @Override
    public void successGenericToPresenter(DataSourceResult dataSourceResult) {
        /**
         * Instancia de peticion exitosa y operacion exitosa de ActualizarAvatarResponse
         */
        if (dataSourceResult.getData() instanceof ActualizarAvatarResponse) {
            ActualizarAvatarResponse response = (ActualizarAvatarResponse) dataSourceResult.getData();
            iListaOpcionesView.sendSuccessAvatarToView(response.getMensaje());
        }

        /**
         * Instancia de peticion exitosa y operacion exitosa de CambiarEmailResponse
         */
        if (dataSourceResult.getData() instanceof CambiarEmailResponse) {
            CambiarEmailResponse response = (CambiarEmailResponse) dataSourceResult.getData();
            iMyEmailView.sendSuccessEmailToView(response.getMensaje());
        }

        /**
         * Instancia de peticion exitosa y operacion exitosa de CambiarContraseniaResponse
         */
        if (dataSourceResult.getData() instanceof CambiarContraseniaResponse) {
            CambiarContraseniaResponse response = (CambiarContraseniaResponse) dataSourceResult.getData();
            iMyPassView.sendSuccessPassToView(response.getMensaje());
        }

        /**
         * Instancia de peticion exitosa y operacion exitosa de DesasociarDispositivoResponse
         */
        if (dataSourceResult.getData() instanceof DesasociarDispositivoResponse) {
            DesasociarDispositivoResponse response = (DesasociarDispositivoResponse) dataSourceResult.getData();
            iPreferDesasociarView.sendSuccessDesasociarToView(response.getMensaje());
        }
    }

    @Override
    public void errorGenericToPresenter(DataSourceResult dataSourceResult) {
        /**
         * Instancia de peticion exitosa y operacion exitosa de ActualizarAvatarResponse
         */
        if (dataSourceResult.getData() instanceof ActualizarAvatarResponse) {
            ActualizarAvatarResponse response = (ActualizarAvatarResponse) dataSourceResult.getData();
            iListaOpcionesView.sendErrorAvatarToView(response.getMensaje());
        }

        /**
         * Instancia de peticion exitosa y operacion erronea de CambiarEmailResponse
         */
        if (dataSourceResult.getData() instanceof CambiarEmailResponse) {
            CambiarEmailResponse response = (CambiarEmailResponse) dataSourceResult.getData();
            iMyEmailView.sendErrorEmailToView(response.getMensaje());
        }

        /**
         * Instancia de peticion exitosa y operacion erronea de CambiarContraseniaResponse
         */
        if (dataSourceResult.getData() instanceof CambiarContraseniaResponse) {
            CambiarContraseniaResponse response = (CambiarContraseniaResponse) dataSourceResult.getData();
            iMyPassView.sendErrorPassToView(response.getMensaje());
        }

        /**
         * Instancia de peticion exitosa y operacion Erronea de DesasociarDispositivoResponse
         */
        if (dataSourceResult.getData() instanceof DesasociarDispositivoResponse) {
            DesasociarDispositivoResponse response = (DesasociarDispositivoResponse) dataSourceResult.getData();
            iPreferDesasociarView.sendErrorDesasociarToView(response.getMensaje());
        }
    }

    /**
     *  ERRORES DE SERVIDOR
     */

    /**
     * Recibe el error del Iteractor y usamos el metodo de sendErrorPassToView para enviar el mensaje
     * de error a nuestra vista iMyPassView, asi no creamos otro metodo adicional en la vista
     *
     * @param mensaje
     */
    @Override
    public void sendErrorServerPassToPresenter(String mensaje) {
        iMyPassView.sendErrorPassToView(mensaje);
    }

    /**
     * Recibe el error del Iteractor y usamos el metodo de sendErrorDesasociarToView para enviar el mensaje
     * de error a nuestra vista iPreferDesasociarView, asi no creamos otro metodo adicional en la vista
     *
     * @param mensaje
     */
    @Override
    public void sendErrorServerDesasociarToPresenter(String mensaje) {
        iPreferDesasociarView.sendErrorDesasociarToView(mensaje);
    }

    /**
     * EXCEPTIONS VARIADOS
     **/

    @Override
    public void showExceptionAvatarToPresenter(String mMessage) {
        iListaOpcionesView.sendErrorAvatarToView(mMessage);
    }

    /**
     * Exception al enviar el Pass al servicio. Usamos el metodo generico de Pass para enviar los errores
     * sendErrorPassToView
     *
     * @param mMessage
     */
    @Override
    public void showExceptionPassToPresenter(String mMessage) {
        iMyPassView.sendErrorPassToView(mMessage);
    }


    @Override
    public void showExceptionDesasociarToPresenter(String mMesage) {
        iPreferDesasociarView.sendErrorDesasociarToView(mMesage);
    }

}
