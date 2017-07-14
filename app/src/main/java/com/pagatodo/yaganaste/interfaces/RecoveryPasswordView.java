package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 22/03/2017.
 */

public interface RecoveryPasswordView extends INavigationView {

    void recoveryPasswordSuccess(String message);

    void recoveryPasswordFailed(String message);
}
