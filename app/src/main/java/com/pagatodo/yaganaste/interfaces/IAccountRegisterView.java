package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAccountRegisterView<E> extends IAccountAddressRegisterView<E> {

    void clientCreatedSuccess(String message);

    void clientCreateFailed(String error);

    void zipCodeInvalid(String message);

}
