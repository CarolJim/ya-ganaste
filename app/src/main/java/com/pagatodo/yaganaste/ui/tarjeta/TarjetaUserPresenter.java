package com.pagatodo.yaganaste.ui.tarjeta;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.BloquearCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EnviarCorreoContactanosRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EstatusCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EnviarCorreoContactanosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.ui._controllers.TarjetaActivity;
import com.pagatodo.yaganaste.ui._manager.GenericPresenterMain;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardViewHome;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyEmailView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyHelpMensajeContactanos;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyPassView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferDesasociarView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserGeneric;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserPresenter;
import com.pagatodo.yaganaste.utils.camera.CameraManager;

/**
 * Created by Armando Sandoval on 10/10/2017.
 */

public class TarjetaUserPresenter extends GenericPresenterMain<IPreferUserGeneric> implements IPreferUserPresenter {

    IMyCardViewHome mView;
    IMyHelpMensajeContactanos iMyHelpMensajeContactanos;
    TarjetaUserIteractor iPreferUserIteractor;

    public TarjetaUserPresenter(IMyCardViewHome mView) {
        // super(mView); Esta linea hace funcionar el PResenterGeneric
        this.mView = mView;
        iPreferUserIteractor = new TarjetaUserIteractor(this);
    }

    public void toPresenterEstatusCuenta(String mTDC) {
        // mView.showLoader("Obteniendo Estatus de Tarjeta");
        mView.showLoader("Obteniendo Estatus de Tarjeta");
        EstatusCuentaRequest estatusCuentaRequest = new EstatusCuentaRequest(mTDC);
        iPreferUserIteractor.toIteractorEstatusCuenta(estatusCuentaRequest);
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

        // Set de instancia de IMyHelpMensajeContactanos
        if (iPreferUserGeneric instanceof IMyHelpMensajeContactanos) {
            this.iMyHelpMensajeContactanos = (IMyHelpMensajeContactanos) iPreferUserGeneric;
        }
    }

    @Override
    public void DesasociarToPresenter() {    }

    @Override
    public void sendErrorServerPresenter(String error) {    }

    @Override
    public void showExceptionToPresenter(String mMesage) {    }

    @Override
    public void sendErrorServerAvatarToPresenter(String s) {    }

    @Override
    public void changeEmailToPresenter(String s, String s1) {    }

    @Override
    public void successGenericToPresenter(DataSourceResult dataSourceResult) {
        /**
         * Instancia de peticion exitosa y operacion exitosa de ActualizarAvatarResponse
         */
        if (dataSourceResult.getData() instanceof EnviarCorreoContactanosResponse) {
            EnviarCorreoContactanosResponse response = (EnviarCorreoContactanosResponse) dataSourceResult.getData();
            iMyHelpMensajeContactanos.sendSuccessMensaje(App.getContext().getString(R.string.correo_enviado));
        }
        if (dataSourceResult.getData() instanceof EstatusCuentaResponse) {
            mView.hideLoader();
            EstatusCuentaResponse response = (EstatusCuentaResponse) dataSourceResult.getData();
            mView.sendSuccessEstatusCuentaToView(response);
        }

        /**
         * Instancia de peticion exitosa y operacion exitosa de BloquearCuentaResponse
         */
        if (dataSourceResult.getData() instanceof BloquearCuentaResponse) {
            mView.hideLoader();
            BloquearCuentaResponse response = (BloquearCuentaResponse) dataSourceResult.getData();
            mView.sendSuccessBloquearCuentaToView(response);
        }
    }

    @Override
    public void errorGenericToPresenter(DataSourceResult dataSourceResult) {
        /**
        * Instancia de peticion exitosa y operacion exitosa de ActualizarAvatarResponse
        */
        mView.hideLoader();
        mView.sendErrorBloquearCuentaToView("No hay internet");
        if (dataSourceResult.getData()instanceof TarjetaActivity){

            mView.sendErrorBloquearCuentaToView("No tienes Acceso a Internet ");
        }
        if (dataSourceResult.getData() instanceof EnviarCorreoContactanosResponse) {
            EnviarCorreoContactanosResponse response = (EnviarCorreoContactanosResponse) dataSourceResult.getData();
            iMyHelpMensajeContactanos.sendErrorEnvioCorreoContactanos(response.getMensaje());
        }
    }

    @Override
    public void changePassToPresenter(String s, String s1) {    }

    @Override
    public void sendErrorServerPassToPresenter(String s) {    }

    @Override
    public void showExceptionPassToPresenter(String s) {    }

    @Override
    public void sendErrorServerDesasociarToPresenter(String s) {    }

    @Override
    public void showExceptionDesasociarToPresenter(String s) {    }

    @Override
    public void toPresenterBloquearCuenta(int operation) {
        mView.showLoader("");
        // Creamos el objeto BloquearCuentaRequest 1= Bloquear 2= Desbloquear
        BloquearCuentaRequest bloquearCuentaRequest = new BloquearCuentaRequest("" + operation);
        iPreferUserIteractor.toIteractorBloquearCuenta(bloquearCuentaRequest);
    }

    @Override
    public void showExceptionBloquearCuentaToPresenter(String s) {
        mView.sendErrorBloquearCuentaToView("No Hay conexion a internet ");

    }

    @Override
    public void sendErrorServerBloquearCuentaToPresenter(String s) {   }

    @Override
    public void sendErrorServerEstatusCuentaToPresenter(String mensaje) {
        mView.hideLoader();
        // mView.sendErrorEstatusCuentaToView(mensaje);
    }

    @Override
    public void enviarCorreoContactanosPresenter(EnviarCorreoContactanosRequest request) {
        iPreferUserIteractor.enviarCorreoContactanos(request);
    }

    @Override
    public void showExceptionCorreoContactanosPresenter(String s) {
        iMyHelpMensajeContactanos.hideLoader();
        iMyHelpMensajeContactanos.sendErrorEnvioCorreoContactanos(s);
    }

    @Override
    public void sendErrorServerCorreoContactanosPresenter(String s) {
        iMyHelpMensajeContactanos.hideLoader();
        iMyHelpMensajeContactanos.sendErrorEnvioCorreoContactanos(s);
    }
}
