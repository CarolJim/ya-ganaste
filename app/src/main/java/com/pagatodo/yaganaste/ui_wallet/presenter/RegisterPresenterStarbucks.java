package com.pagatodo.yaganaste.ui_wallet.presenter;

import android.content.Context;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.RegisterUserStarbucks;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.RegisterStarbucksRequest;
import com.pagatodo.yaganaste.interfaces.View;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui_wallet.interactors.LoginIteractorStarbucks;
import com.pagatodo.yaganaste.ui_wallet.interactors.RegisterIteractorStarbucks;
import com.pagatodo.yaganaste.ui_wallet.interfaces.Iloginstarbucks;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IregisterStarbukss;
import com.pagatodo.yaganaste.ui_wallet.interfaces.Iregisterstarbucks;
import com.pagatodo.yaganaste.utils.Utils;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.PREREGISTRO;

/**
 * Created by asandovals on 25/04/2018.
 */

public class RegisterPresenterStarbucks implements IregisterStarbukss {


    Iregisterstarbucks registerview;
    private Context context;
    private Preferencias prefs = App.getInstance().getPrefs();
    RegisterIteractorStarbucks registerIteractorStarbucks;

    public RegisterPresenterStarbucks(Context context) {
        this.context= context;
        registerIteractorStarbucks = new RegisterIteractorStarbucks(this);
    }
    public void setIView(View accountView) {
        this.registerview = (Iregisterstarbucks) accountView;
    }

    @Override
    public void onSucces(WebService ws, Object msgSuccess) {
        switch (ws) {
            case PREREGISTRO:
                registerview.registerstarsucced();
                break;
        }
    }

    @Override
    public void onError(WebService ws, Object error) {
        registerview.registerfail(error.toString());
    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void registerStarbucks(String tarjeta, String pin) {
       // registerview.showLoader("");
        RegisterStarbucksRequest registerStarbucksRequest = new RegisterStarbucksRequest();
        registerStarbucksRequest.setNoTarjetaSbx(tarjeta);
        registerStarbucksRequest.setCodigoVerificador(pin);
        registerStarbucksRequest.setUdid(Utils.getUdid(App.getContext()));
        registerIteractorStarbucks.register(registerStarbucksRequest);

        RegisterUserStarbucks registerUser = RegisterUserStarbucks.getInstance();
        registerUser.setNumeroTarjeta(tarjeta);
        registerUser.setCodigoVerificador(pin);


    }

}
