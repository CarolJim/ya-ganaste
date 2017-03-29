package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAccountBasicRegisterView extends IAccountView {

    public void validationPasswordSucces();
    public void validationPasswordFailed(String message);
    public void userCreatedSuccess(String message);

}
