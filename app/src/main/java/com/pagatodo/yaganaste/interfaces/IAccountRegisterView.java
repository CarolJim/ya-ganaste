package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAccountRegisterView<E> extends IAccountAddressRegisterView<E> {

    public void clientCreatedSuccess(String message);
    public void clientCreateFailed(String error);
    public void zipCodeInvalid(String message);

}
