package com.pagatodo.yaganaste.ui.preferuser.presenters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarDatosCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.BloquearCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CambiarContraseniaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CambiarEmailRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DesasociarDispositivoRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EstatusCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EnviarCorreoContactanosRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActualizarAvatarResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActualizarDatosCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CambiarContraseniaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CambiarEmailResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DesasociarDispositivoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EnviarCorreoContactanosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._manager.GenericPresenterMain;
import com.pagatodo.yaganaste.ui.preferuser.interfases.ICropper;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyEmailView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyHelpMensajeContactanos;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyPassView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferDesasociarView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserGeneric;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserIteractor;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserPresenter;
import com.pagatodo.yaganaste.ui.preferuser.iteractors.PreferUserIteractor;
import com.pagatodo.yaganaste.utils.camera.CameraManager;

import static com.pagatodo.yaganaste.utils.Recursos.URL_PHOTO_USER;

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
    IMyHelpMensajeContactanos iMyHelpMensajeContactanos;
    IMyEmailView iMyEmailView;
    IMyPassView iMyPassView;
    IMyCardView iMyCardView;

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

        // Set de instancia de IMyPassView
        if (iPreferUserGeneric instanceof IMyCardView) {
            this.iMyCardView = (IMyCardView) iPreferUserGeneric;
        }

        // Set de instancia de IMyHelpMensajeContactanos
        if (iPreferUserGeneric instanceof IMyHelpMensajeContactanos) {
            this.iMyHelpMensajeContactanos = (IMyHelpMensajeContactanos) iPreferUserGeneric;
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

    public void sendPresenterUpdateDatosCuenta(ActualizarDatosCuentaRequest datosCuentaRequest) {
        mView.showLoader("Actualizando Datos");
        iPreferUserIteractor.sendIteractorDatosCuenta(datosCuentaRequest);
    }

    /**
     * Enviamos al Iteractor la operacion para Bloquear o Desbloquear temporalmente la TDC,
     * depenciendo de la constante @param operation
     * @param operation
     */
    @Override
    public void toPresenterBloquearCuenta(int operation) {
        iMyCardView.showLoader("Actualizando Datos");
        // Creamos el objeto BloquearCuentaRequest 1= Bloquear 2= Desbloquear
        BloquearCuentaRequest bloquearCuentaRequest = new BloquearCuentaRequest("" + operation);
        iPreferUserIteractor.toIteractorBloquearCuenta(bloquearCuentaRequest);
    }

    /**
     * Enviamos al Iteractor la operacion para obtener el estatus de la TDC,
     * @param mTDC
     */
    public void toPresenterEstatusCuenta(String mTDC) {
        mView.showLoader("Obteniendo Estatus de Tarjeta");
        EstatusCuentaRequest estatusCuentaRequest = new EstatusCuentaRequest(mTDC);
        iPreferUserIteractor.toIteractorEstatusCuenta(estatusCuentaRequest);
    }

    @Override
    public void showExceptionBloquearCuentaToPresenter(String s) {

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
        if (dataSourceResult.getData() instanceof EnviarCorreoContactanosResponse) {
            EnviarCorreoContactanosResponse response = (EnviarCorreoContactanosResponse) dataSourceResult.getData();
            iMyHelpMensajeContactanos.sendSuccessMensaje(App.getContext().getString(R.string.correo_enviado));
        }/**
         * Instancia de peticion exitosa y operacion exitosa de ActualizarAvatarResponse
         */
        if (dataSourceResult.getData() instanceof ActualizarAvatarResponse) {
            ActualizarAvatarResponse response = (ActualizarAvatarResponse) dataSourceResult.getData();
            String mUserImage = response.getData().getImagenAvatarURL();
            String[] urlSplit = mUserImage.split("_");
            if (urlSplit.length > 1) {
                App.getInstance().getPrefs().saveData(URL_PHOTO_USER, urlSplit[0] + "_M.png");
            }
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

        /**
         * Instancia de peticion exitosa y operacion exitosa de ActualizarDatosCuentaResponse
         */
        if (dataSourceResult.getData() instanceof ActualizarDatosCuentaResponse) {
            mView.hideLoader();
            ActualizarDatosCuentaResponse response = (ActualizarDatosCuentaResponse) dataSourceResult.getData();
            mView.sendSuccessDatosCuentaToView(response);
        }

        /**
         * Instancia de peticion exitosa y operacion exitosa de BloquearCuentaResponse
         */
        if (dataSourceResult.getData() instanceof BloquearCuentaResponse) {
            iMyCardView.hideLoader();
            BloquearCuentaResponse response = (BloquearCuentaResponse) dataSourceResult.getData();
            iMyCardView.sendSuccessBloquearCuentaToView(response);
        }

        /**
         * Instancia de peticion exitosa y operacion exitosa de BloquearCuentaResponse
         */
        if (dataSourceResult.getData() instanceof EstatusCuentaResponse) {
            mView.hideLoader();
            EstatusCuentaResponse response = (EstatusCuentaResponse) dataSourceResult.getData();
            mView.sendSuccessEstatusCuentaToView(response);
        }
    }

    @Override
    public void errorGenericToPresenter(DataSourceResult dataSourceResult) {
        /**
         * Instancia de peticion exitosa y operacion exitosa de ActualizarAvatarResponse
         */
        if (dataSourceResult.getData() instanceof EnviarCorreoContactanosResponse) {
            EnviarCorreoContactanosResponse response = (EnviarCorreoContactanosResponse) dataSourceResult.getData();
            iMyHelpMensajeContactanos.sendErrorEnvioCorreoContactanos(response.getMensaje());
        }

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

        /**
         * Instancia de peticion exitosa y operacion exitosa de ActualizarDatosCuentaResponse
         */
        if (dataSourceResult.getData() instanceof ActualizarDatosCuentaResponse) {
            mView.hideLoader();
            ActualizarDatosCuentaResponse response = (ActualizarDatosCuentaResponse) dataSourceResult.getData();
            mView.sendErrorDatosCuentaToView(response.getMensaje());
        }
        /**
         * Instancia de peticion exitosa y operacion exitosa de BloquearCuentaResponse
         */
        if (dataSourceResult.getData() instanceof BloquearCuentaResponse) {
            iMyCardView.hideLoader();
            BloquearCuentaResponse response = (BloquearCuentaResponse) dataSourceResult.getData();
            iMyCardView.sendErrorBloquearCuentaToView(response.getMensaje());
        }

        /**
         * Instancia de peticion exitosa y operacion exitosa de BloquearCuentaResponse
         */
        if (dataSourceResult.getData() instanceof EstatusCuentaResponse) {
            mView.hideLoader();
            EstatusCuentaResponse response = (EstatusCuentaResponse) dataSourceResult.getData();
            mView.sendErrorEstatusCuentaToView(response.getMensaje());
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
     *  Recibe el error del Iteractor y usamos el metodo de sendErrorDatosCuentaToView para enviar el mensaje
     * de error a nuestra vista iPreferDesasociarView, asi no creamos otro metodo adicional en la vista
     * @param mensaje
     */
    public void sendErrorServerDatosCuentaToPresenter(String mensaje) {
        mView.hideLoader();
        mView.sendErrorDatosCuentaToView(mensaje);
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

    public void showExceptionDatosCuentaToPresenter(String mensaje) {
        iMyCardView.hideLoader();
        iMyCardView.sendErrorBloquearCuentaToView(mensaje);
    }

    @Override
    public void sendErrorServerBloquearCuentaToPresenter(String mensaje) {
        iMyCardView.hideLoader();
        iMyCardView.sendErrorBloquearCuentaToView(mensaje);
    }

    @Override
    public void sendErrorServerEstatusCuentaToPresenter(String mensaje) {
        mView.hideLoader();
        mView.sendErrorEstatusCuentaToView(mensaje);
    }
    @Override
    public void enviarCorreoContactanosPresenter(EnviarCorreoContactanosRequest request) {
        iPreferUserIteractor.enviarCorreoContactanos(request);
    }

    @Override
    public void showExceptionCorreoContactanosPresenter(String mMesage) {
        iMyHelpMensajeContactanos.hideLoader();
        iMyHelpMensajeContactanos.sendErrorEnvioCorreoContactanos(mMesage);
    }

    @Override
    public void sendErrorServerCorreoContactanosPresenter(String s) {
        iMyHelpMensajeContactanos.hideLoader();
        iMyHelpMensajeContactanos.sendErrorEnvioCorreoContactanos(s);
    }


}
