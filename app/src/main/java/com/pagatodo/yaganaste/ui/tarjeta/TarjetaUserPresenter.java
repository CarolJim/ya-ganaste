package com.pagatodo.yaganaste.ui.tarjeta;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.BloquearCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EnviarCorreoContactanosRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EstatusCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.ui._controllers.TarjetaActivity;
import com.pagatodo.yaganaste.ui._manager.GenericPresenterMain;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IMyCardViewHome;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserGeneric;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserIteractor;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserPresenter;
import com.pagatodo.yaganaste.utils.camera.CameraManager;

/**
 * Created by Armando Sandoval on 10/10/2017.
 */

public class TarjetaUserPresenter extends GenericPresenterMain<IPreferUserGeneric> implements IPreferUserPresenter {

    IMyCardViewHome mView;
    TarjetaUserIteractor iPreferUserIteractor;


    public TarjetaUserPresenter(IMyCardViewHome mView) {
        // super(mView); Esta linea hace funcionar el PResenterGeneric
        this.mView = mView;

        iPreferUserIteractor = new TarjetaUserIteractor(this);
    }

    /**
     * Enviamos al Iteractor la operacion para obtener el estatus de la TDC,
     * @param mTDC
     */




    public void toPresenterEstatusCuenta(String mTDC) {
      // mView.showLoader("Obteniendo Estatus de Tarjeta");
         mView.showLoader("Obteniendo Estatus de Tarjeta");
        EstatusCuentaRequest estatusCuentaRequest = new EstatusCuentaRequest(mTDC);
        iPreferUserIteractor.toIteractorEstatusCuenta(estatusCuentaRequest);
    }

    @Override
    public void DesasociarToPresenter() {

    }

    @Override
    public void sendErrorServerPresenter(String error) {

    }

    @Override
    public void openMenuPhoto(int i, CameraManager cameraManager) {

    }

    @Override
    public void sendPresenterActualizarAvatar(ActualizarAvatarRequest avatarRequest) {

    }

    @Override
    public void showExceptionAvatarToPresenter(String s) {

    }

    @Override
    public void showExceptionToPresenter(String mMesage) {

    }

    @Override
    public void sendErrorServerAvatarToPresenter(String s) {

    }

    @Override
    public void changeEmailToPresenter(String s, String s1) {

    }

    @Override
    public void successGenericToPresenter(DataSourceResult dataSourceResult) {
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

    }

    @Override
    public void changePassToPresenter(String s, String s1) {

    }

    @Override
    public void sendErrorServerPassToPresenter(String s) {

    }

    @Override
    public void showExceptionPassToPresenter(String s) {

    }

    @Override
    public void sendErrorServerDesasociarToPresenter(String s) {

    }

    @Override
    public void showExceptionDesasociarToPresenter(String s) {

    }

    @Override
    public void toPresenterBloquearCuenta(int operation) {
        mView.showLoader("Actualizando Datos");
        // Creamos el objeto BloquearCuentaRequest 1= Bloquear 2= Desbloquear
        BloquearCuentaRequest bloquearCuentaRequest = new BloquearCuentaRequest("" + operation);
        iPreferUserIteractor.toIteractorBloquearCuenta(bloquearCuentaRequest);
    }

    @Override
    public void showExceptionBloquearCuentaToPresenter(String s) {

    }

    @Override
    public void sendErrorServerBloquearCuentaToPresenter(String s) {

    }

    @Override
    public void sendErrorServerEstatusCuentaToPresenter(String mensaje) {
        mView.hideLoader();
       // mView.sendErrorEstatusCuentaToView(mensaje);
    }

    @Override
    public void enviarCorreoContactanosPresenter(EnviarCorreoContactanosRequest request) {

    }

    @Override
    public void showExceptionCorreoContactanosPresenter(String s) {

    }

    @Override
    public void sendErrorServerCorreoContactanosPresenter(String s) {

    }
}
