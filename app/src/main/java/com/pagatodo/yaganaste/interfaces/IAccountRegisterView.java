package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAccountRegisterView extends IAccountAddressRegisterView {

    public void clientCreatedSuccess(String message);
    public void clientCreateFailed(String error);

}
