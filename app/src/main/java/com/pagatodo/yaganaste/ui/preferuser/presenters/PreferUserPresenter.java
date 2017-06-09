package com.pagatodo.yaganaste.ui.preferuser.presenters;

import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DesasociarDispositivoRequest;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferDesasociarView;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserIteractor;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserPresenter;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserTest;
import com.pagatodo.yaganaste.ui.preferuser.iteractors.PreferUserIteractor;

/**
 * Created by Francisco Manzo on 08/06/2017.
 * Presenter general para la actividad PreferUserActivity
 * Para gestionar todos los eventos de los fragmentos
 */

public class PreferUserPresenter implements IPreferUserPresenter{

    PreferUserActivity mView;
    IPreferUserIteractor iPreferUserIteractor;
    IPreferDesasociarView iPreferDesasociarView;

    public PreferUserPresenter(PreferUserActivity mView) {
        this.mView = mView;

        iPreferUserIteractor = new PreferUserIteractor(this);
    }

    public void setIView( IPreferUserTest iPreferUserTest){
        this.iPreferDesasociarView = (IPreferDesasociarView) iPreferUserTest;
    }

    @Override
    public void testToastToPresenter() {
        DesasociarDispositivoRequest desasociarRequest = new DesasociarDispositivoRequest();
        desasociarRequest.setIdComponente(RequestHeaders.getComponent());
        desasociarRequest.setNombreUsuario(RequestHeaders.getUsername());
        desasociarRequest.setIdDispositivo(RequestHeaders.getDevice());
        desasociarRequest.setContentType("application/json");
        desasociarRequest.setTokenSesion(RequestHeaders.getTokensesion());
        desasociarRequest.setIdCuenta(RequestHeaders.getIdCuenta());

        iPreferUserIteractor.testToastToIteracto(desasociarRequest);
    }

    @Override
    public void successToPresenter() {
        iPreferDesasociarView.successToasToView();
    }
}
