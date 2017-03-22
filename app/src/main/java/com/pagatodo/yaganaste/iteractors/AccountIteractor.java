package com.pagatodo.yaganaste.iteractors;

import android.util.Log;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarEstatusUsuarioRequest;
import com.pagatodo.yaganaste.interfaces.IAccountIteractor;
import com.pagatodo.yaganaste.interfaces.IAccountManager;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.net.RequestHeaders;

/**
 * Created by flima on 22/03/2017.
 */

public class AccountIteractor implements IAccountIteractor,IRequestResult {

    private String TAG = AccountIteractor.class.getName();
    private IAccountManager accountManager;

    public AccountIteractor(IAccountManager accountManager){
        this.accountManager = accountManager;

    }


    @Override
    public void validateUserStatus(String usuario) {
        RequestHeaders.setUsername(usuario); // Seteamos el usuario en el Header
        ValidarEstatusUsuarioRequest request = new ValidarEstatusUsuarioRequest(usuario);
        ApiAdtvo.validarEstatusUsuario(request,this);
    }


    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {

        switch (dataSourceResult.getWebService()) {

            case VALIDAR_ESTATUS_USUARIO:
                Log.i(TAG,"onSuccess:");
                setUserStatus();
                break;

            default:
                break;

        }
    }

    @Override
    public void onFailed(DataSourceResult error) {

    }

    private void setUserStatus(){

        accountManager.setUserStatus(true);

    }
}
