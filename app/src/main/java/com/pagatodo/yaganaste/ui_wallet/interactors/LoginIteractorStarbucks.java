package com.pagatodo.yaganaste.ui_wallet.interactors;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.LoginStarbucksRequest;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.net.ApiStarbucks;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IloginIteractorStarbucks;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IloginStarbucksss;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ASIGNAR_NIP;

/**
 * Created by asandovals on 19/04/2018.
 */

public class LoginIteractorStarbucks implements IloginIteractorStarbucks, IRequestResult {
    IloginStarbucksss iloginStarbucksss;

    public LoginIteractorStarbucks(IloginStarbucksss iloginStarbucksss) {
        this.iloginStarbucksss = iloginStarbucksss;
    }



    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        switch (dataSourceResult.getWebService()) {

        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        if (error != null && error.getWebService() == ASIGNAR_NIP) {

        }

    }

    @Override
    public void login(LoginStarbucksRequest request) {

        try {
            ApiStarbucks.loginStarbucks(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
           // accountManager.onError(CREAR_USUARIO_CLIENTE, "");
        }


    }
}
