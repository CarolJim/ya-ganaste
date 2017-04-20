package com.pagatodo.yaganaste.ui.otp.controllers;

import com.pagatodo.yaganaste.freja.Errors;

/**
 * @author Juan Guerra on 18/04/2017.
 */

public interface OtpView {
    void onOtpGenerated(String otp);
    void showError(Errors error);
}
