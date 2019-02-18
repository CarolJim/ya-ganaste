package com.pagatodo.yaganaste.modules.payments.mobiletopup.interactor;

public interface MobileTopUpIntercator {
    void validateFields(String phone, Double monto, int getLongitudReferencia, boolean isIAVE);
}
