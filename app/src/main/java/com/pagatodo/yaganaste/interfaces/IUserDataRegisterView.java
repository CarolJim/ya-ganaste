package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 27/03/2017.
 */

public interface IUserDataRegisterView<T> extends INavigationView {
    public void isEmailAvaliable();
    public void isEmailRegistered();
    public void validationPasswordSucces();
    public void validationPasswordFailed(String message);
}

