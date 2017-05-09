package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 22/03/2017.
 */

public interface RecoveryPasswordView extends INavigationView  {

    public void recoveryPasswordSuccess(String message);
    public void recoveryPasswordFailed(String message);
}
