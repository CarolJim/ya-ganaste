package com.pagatodo.yaganaste.freja.otp.presenter;

import android.content.Context;
import com.pagatodo.yaganaste.freja.otp.manager.OtpManager;
import com.pagatodo.yaganaste.freja.otp.iteractor.OtpIteractor;
import com.pagatodo.yaganaste.freja.otp.iteractor.OtpIteractorImp;

/**
 * @author Juan Guerra on 31/03/2017.
 */

public abstract class OtpPresenterAbs implements OtpPResenter, OtpManager {

    private OtpIteractor otpIteractor;

    public OtpPresenterAbs(Context context) {
        this.otpIteractor = new OtpIteractorImp(this);
        this.otpIteractor.init(context);
    }

    @Override
    public void generateOTP(String pin) {
        otpIteractor.generateOtp(pin.getBytes());
    }

}