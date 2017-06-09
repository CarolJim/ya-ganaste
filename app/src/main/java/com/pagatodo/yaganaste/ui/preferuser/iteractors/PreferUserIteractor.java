package com.pagatodo.yaganaste.ui.preferuser.iteractors;

import android.util.Log;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.Request;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DesasociarDispositivoRequest;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserIteractor;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;

/**
 * Created by Francisco Manzo on 08/06/2017.
 */

public class PreferUserIteractor implements IPreferUserIteractor, IRequestResult {

    PreferUserPresenter preferUserPresenter;
    private boolean logOutBefore;

    public PreferUserIteractor(PreferUserPresenter preferUserPresenter) {
        this.preferUserPresenter = preferUserPresenter;
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        Log.d("PreferUserIteractor", "Success: " + dataSourceResult);
    }

    @Override
    public void onFailed(DataSourceResult error) {
        Log.d("PreferUserIteractor", "Error: " + error);
    }

    @Override
    public void testToastToIteracto(Request request) {

        if(request instanceof DesasociarDispositivoRequest){

        }

        if (!RequestHeaders.getTokensesion().isEmpty()) {
            logOutBefore = true;
            logout();
        } else {
            logOutBefore = false;
            desasociaDispositivo((DesasociarDispositivoRequest) request);

  /*          switch (this.operationAccount) {
                case CREATE_USER:
                    createUserClient((CrearUsuarioClienteRequest) this.requestAccountOperation); // Creamos usuario.
                    break;

                case LOGIN:
                    login((IniciarSesionRequest) this.requestAccountOperation);
                    break;
            }*/

        }
        //preferUserPresenter.successToPresenter();
    }

    public void logout() {
        try {
            ApiAdtvo.cerrarSesion(this);// Se envia null ya que el Body no aplica.
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    public void desasociaDispositivo(DesasociarDispositivoRequest request) {
        try {
            ApiAdtvo.desasociarDispositivo(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }
}
