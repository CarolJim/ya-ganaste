package com.pagatodo.yaganaste.ui_wallet.presenter;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.DispositivoStartBucks;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.LoginStarbucksRequest;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui_wallet.interactors.LoginIteractorStarbucks;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IloginStarbucksss;
import com.pagatodo.yaganaste.ui_wallet.interfaces.Iloginstarbucks;
import com.pagatodo.yaganaste.utils.Utils;

import static com.pagatodo.yaganaste.utils.Recursos.PUBLIC_STARBUCKS_KEY_RSA;
import static com.pagatodo.yaganaste.utils.Recursos.SHA_256_STARBUCKS;

/**
 * Created by asandovals on 19/04/2018.
 */

public class LoginPresenterStarbucks implements IloginStarbucksss {

    private Iloginstarbucks  loginview;

    LoginIteractorStarbucks loginIteractorStarbucks;

    private Preferencias prefs = App.getInstance().getPrefs();

    public LoginPresenterStarbucks(Iloginstarbucks loginview) {
        this.loginview = loginview;
    }

    @Override
    public void onSucces(WebService ws, Object msgSuccess) {

    }

    @Override
    public void onError(WebService ws, Object error) {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void login(String usuario, String password) {


        prefs.saveData(SHA_256_STARBUCKS, Utils.cipherRSA(password, PUBLIC_STARBUCKS_KEY_RSA));//Contraseña Starbucks
        LoginStarbucksRequest request = new LoginStarbucksRequest();
        request.setEmail(usuario);
        request.setContrasenia(prefs.loadData(SHA_256_STARBUCKS));
        DispositivoStartBucks datadispositivo = new DispositivoStartBucks();
        datadispositivo.setUdid(Utils.getUdid(App.getContext()));
        datadispositivo.setIdTokenNotificacion("");
        datadispositivo.setLatitud("0.0");
        datadispositivo.setLongitud("0.0");
        request.setDispositivoStartBucks(datadispositivo);
        request.setFuente("Movil");
        loginIteractorStarbucks.login(request);

    }
}
