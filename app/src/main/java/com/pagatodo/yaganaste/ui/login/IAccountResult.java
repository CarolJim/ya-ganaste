package com.pagatodo.yaganaste.ui.login;

/**
 * Created by flima on 20/02/2017.
 */

public interface IAccountResult {

    public void onLoginSuccess(Object data);
    public void onLoginFailed(Object errorLogin);
    public void accountCreated(Object data);
    public void onRegisterFailed(Object errorRegister);
    public void onRecoveryPasswordSuccess(Object data);
    public void onRecoveryPasswordFailed(Object data);
    public void onLogOutSucces();
}
