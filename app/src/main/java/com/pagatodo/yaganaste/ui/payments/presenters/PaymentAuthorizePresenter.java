package com.pagatodo.yaganaste.ui.payments.presenters;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.ui.otp.presenters.OtpPresenterImp;
import com.pagatodo.yaganaste.ui.payments.interactors.PaymentAuthorizeInteractor;
import com.pagatodo.yaganaste.ui.payments.interactors.interfaces.IPaymentAuthorizeInteractor;
import com.pagatodo.yaganaste.ui.payments.managers.PaymentAuthorizeManager;
import com.pagatodo.yaganaste.ui.payments.presenters.interfaces.IPaymentAuthorizePresenter;
import com.pagatodo.yaganaste.utils.Utils;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_FORMATO_CONTRASENIA;
import static com.pagatodo.yaganaste.utils.Recursos.PUBLIC_KEY_RSA;

/**
 * Created by Jordan on 14/08/2017.
 */

public class PaymentAuthorizePresenter extends OtpPresenterImp implements IPaymentAuthorizePresenter {

    IPaymentAuthorizeInteractor interactor;
    PaymentAuthorizeManager manager;


    public PaymentAuthorizePresenter(PaymentAuthorizeManager manager) {
        super(App.getInstance(), manager);
        interactor = new PaymentAuthorizeInteractor(this);
        this.manager = manager;
    }

    @Override
    public void validatePasswordFormat(String password) {
        manager.showLoader("");
        interactor.validatePasswordFormat(Utils.cipherRSA(password, PUBLIC_KEY_RSA));
    }

    @Override
    public void onSuccess(WebService ws, Object success) {
        if (ws == VALIDAR_FORMATO_CONTRASENIA) {
            boolean validatePass = ((int) success == 0);
            if (validatePass) {
                manager.validationPasswordSucces();
            } else {
                manager.validationPasswordFailed(App.getContext().getString(R.string.password_incorrect));
            }
        }
    }

    @Override
    public void onError(WebService ws, Object error) {
        if (ws == VALIDAR_FORMATO_CONTRASENIA) {
            manager.validationPasswordFailed(error.toString());
        }
    }

    @Override
    public void onConexionError(String message) {
        manager.showError(message);
    }
}
