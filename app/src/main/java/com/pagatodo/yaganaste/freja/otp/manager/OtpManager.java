package com.pagatodo.yaganaste.freja.otp.manager;

import com.pagatodo.yaganaste.freja.general.ErrorFmcManager;

/**
 * @author Juan Guerra on 03/04/2017.
 */

public interface OtpManager extends ErrorFmcManager {
    void onOtpGenerated(String otp);
}
