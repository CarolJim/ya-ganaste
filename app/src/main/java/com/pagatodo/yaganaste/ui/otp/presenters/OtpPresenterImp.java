package com.pagatodo.yaganaste.ui.otp.presenters;

import android.content.Context;

import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.otp.presenter.OtpPresenterAbs;
import com.pagatodo.yaganaste.ui.otp.controllers.OtpView;

/**
 * @author Juan Guerra on 18/04/2017.
 */

public class OtpPresenterImp extends OtpPresenterAbs {

    public static final String TAG = OtpPresenterImp.class.getSimpleName();

    private OtpView otpView;

    public OtpPresenterImp(Context context, OtpView otpView) {
        super(context);
        this.otpView = otpView;
    }

    @Override
    public void onError(Errors error) {
        otpView.showError(error);
    }

    @Override
    public void onOtpGenerated(String otp) {
        otpView.onOtpGenerated(otp);
    }

}