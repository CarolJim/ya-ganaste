package com.pagatodo.yaganaste.freja.otp.presenter;

import android.content.Context;

import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.otp.iteractor.OtpIteractorImp;
import com.pagatodo.yaganaste.freja.otp.manager.OtpManager;
import com.pagatodo.yaganaste.freja.otp.iteractor.OtpIteractor;
import com.pagatodo.yaganaste.freja.token.presenter.TokenPresenterAbs;

/**
 * @author Juan Guerra on 31/03/2017.
 */

public abstract class OtpPresenterAbs extends TokenPresenterAbs implements OtpPResenter, OtpManager {

    private OtpIteractor otpIteractor;

    public OtpPresenterAbs(Context context) {
        super(context);
        otpIteractor = new OtpIteractorImp(this);
        otpIteractor.init(context);
    }

    @Override
    public void generateOTP(String pin) {
        if (hasOfflineToken()) {
            otpIteractor.generateOtp(pin.getBytes());
        } else {
            onError(Errors.NO_OFFLINE_TOKEN);
        }
    }

}