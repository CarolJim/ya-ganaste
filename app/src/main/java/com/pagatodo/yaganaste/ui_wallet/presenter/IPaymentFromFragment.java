package com.pagatodo.yaganaste.ui_wallet.presenter;

/**
 * Created by FranciscoManzo on 10/01/2018.
 */

public interface IPaymentFromFragment {
    void onErrorValidateService(String string);

    void onSuccessValidateService(Double result);
}
