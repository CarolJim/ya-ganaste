package com.pagatodo.yaganaste.ui.preferuser.presenters;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DesasociarDispositivoRequest;
import com.pagatodo.yaganaste.net.Api;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferDesasociarView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserIteractor;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserPresenter;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserTest;
import com.pagatodo.yaganaste.ui.preferuser.iteractors.PreferUserIteractor;

import java.util.Map;

/**
 * Created by Francisco Manzo on 08/06/2017.
 * Presenter general para la actividad PreferUserActivity
 * Para gestionar todos los eventos de los fragmentos
 */

public class PreferUserPresenter implements IPreferUserPresenter {

    PreferUserActivity mView;
    IPreferUserIteractor iPreferUserIteractor;
    IPreferDesasociarView iPreferDesasociarView;

    public PreferUserPresenter(PreferUserActivity mView) {
        this.mView = mView;

        iPreferUserIteractor = new PreferUserIteractor(this);
    }

    public void setIView(IPreferUserTest iPreferUserTest) {
        this.iPreferDesasociarView = (IPreferDesasociarView) iPreferUserTest;
    }

    /**
     * Se encarga de enviar la peticion al Iteractor para Desasociar. Msotramos el Loader mientras
     * tanto. Arma la peticion del Request para enviaro an Iteractor
     */
    @Override
    public void DesasociarToPresenter() {
        mView.showLoader("Procesando. Por favor, espere . . .");
        DesasociarDispositivoRequest desasociarRequest = new DesasociarDispositivoRequest();
        iPreferUserIteractor.desasociarToIteracto(desasociarRequest);
    }

    /**
     * Exito en la conexion al servidor y procedimiento de Desasociar. Cerrarmos el Loader y enviamos
     * el control a la vista
     * @param mensaje
     */
    @Override
    public void sendSuccessPresenter(String mensaje) {
        mView.hideLoader();
        iPreferDesasociarView.sendSuccessView(mensaje);
    }

    /**
     * Exito en la conexion al servidor pero error en codigo de respuesta de procedimiento de
     * Desasociar. Cerrarmos el Loader y enviamos
     * el control a la vista
     * @param mensaje
     */
    @Override
    public void sendErrorPresenter(String mensaje) {
        mView.hideLoader();
        iPreferDesasociarView.sendErrorView(mensaje);
    }

    /**
     * Error en la conexion al servidor y procedimiento de Desasociar. Cerrarmos el Loader y enviamos
     * el control a la vista
     * @param error
     */
    @Override
    public void sendErrorServerPresenter(String error) {
        mView.hideLoader();
        iPreferDesasociarView.sendErrorServerView(error);
    }
}
