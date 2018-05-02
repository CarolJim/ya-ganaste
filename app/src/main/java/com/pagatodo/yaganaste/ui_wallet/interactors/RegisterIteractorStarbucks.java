package com.pagatodo.yaganaste.ui_wallet.interactors;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.RegisterUserStarbucks;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.RegisterStarbucksRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.starbucks.PreRegisterStarbucksRespónse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.net.ApiStarbucks;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IloginStarbucksss;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IregisterIteractorStarbucks;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IregisterStarbukss;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.LOGINSTARBUCKS;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.PREREGISTRO;

/**
 * Created by asandovals on 25/04/2018.
 */

public class RegisterIteractorStarbucks implements IregisterIteractorStarbucks,IRequestResult {

    IregisterStarbukss iregisterStarbukss;



    public RegisterIteractorStarbucks(IregisterStarbukss registerStarbukss) {
        this.iregisterStarbukss = registerStarbukss;
    }

    @Override
    public void register(RegisterStarbucksRequest request) {

        try {
            ApiStarbucks.preregistroStarbucks(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            iregisterStarbukss.onError(PREREGISTRO, e.toString());
        }
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        switch (dataSourceResult.getWebService()) {
            case PREREGISTRO:
                    validatarjeta(dataSourceResult);
                break;
        }
    }

    private void validatarjeta(DataSourceResult dataSourceResult) {

        PreRegisterStarbucksRespónse data = (PreRegisterStarbucksRespónse) dataSourceResult.getData();

        if (data.getCodigo()==0){
            RegisterUserStarbucks registerUser = RegisterUserStarbucks.getInstance();
            registerUser.setId_PreRegistroMovil(Integer.parseInt(data.getIdPreregistro()));
            iregisterStarbukss.onSucces(PREREGISTRO,dataSourceResult);

        }else{
            RegisterUserStarbucks registerUserStarbucks = RegisterUserStarbucks.getInstance();
            registerUserStarbucks.resetRegisterUserstarbucks();
            iregisterStarbukss.onError(PREREGISTRO,data.getDescripcion().toString());
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        iregisterStarbukss.onError(PREREGISTRO,error.toString());

    }
}
