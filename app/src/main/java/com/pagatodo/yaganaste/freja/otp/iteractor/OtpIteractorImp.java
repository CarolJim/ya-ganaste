package com.pagatodo.yaganaste.freja.otp.iteractor;

import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.general.FmcIteractorImp;
import com.pagatodo.yaganaste.freja.otp.manager.OtpManager;
import com.verisec.freja.mobile.core.exceptions.FmcInternalException;

import java.util.Arrays;

/**
 * @author Juan Guerra on 03/04/2017.
 */

public class OtpIteractorImp extends FmcIteractorImp implements OtpIteractor {

    private OtpManager otpManager;

    public OtpIteractorImp(OtpManager otpManager) {
        this.otpManager = otpManager;
    }

    @Override
    public void generateOtp(byte[] pin) {
        try {
            String otp = fmcManager.generateOTPValue(pin);
            otpManager.onOtpGenerated(otp);
        } catch (FmcInternalException e) {
            otpManager.onError(Errors.cast(e));
        }
        Arrays.fill(pin, (byte) 0XF7);
    }

    @Override
    public void throwInitException(Exception e) {
        otpManager.onError(Errors.cast(e));
    }
}