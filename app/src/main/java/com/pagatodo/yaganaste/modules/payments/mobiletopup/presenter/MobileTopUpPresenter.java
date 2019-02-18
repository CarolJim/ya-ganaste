package com.pagatodo.yaganaste.modules.payments.mobiletopup.presenter;

public interface MobileTopUpPresenter {
    void validateFields(String phone, Double monto, int getLongitudReferencia, boolean isIAVE);
    void onNumberEmpty();
    void onNumberError();
    void onMontoError();
    void onSuccess(Double monto);
}
