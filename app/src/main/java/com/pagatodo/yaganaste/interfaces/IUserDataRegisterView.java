package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 27/03/2017.
 */

public interface IUserDataRegisterView<T> extends INavigationView {
    void isEmailAvaliable();

    void isEmailRegistered();

    void validationPasswordSucces();

    void validationPasswordFailed(String message);
}

