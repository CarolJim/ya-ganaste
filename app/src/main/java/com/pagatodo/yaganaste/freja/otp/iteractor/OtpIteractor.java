package com.pagatodo.yaganaste.freja.otp.iteractor;

import com.pagatodo.yaganaste.freja.general.FmcIteractor;

/**
 * @author Juan Guerra on 03/04/2017.
 */

public interface OtpIteractor extends FmcIteractor {
    void generateOtp(byte[] pin);
}
