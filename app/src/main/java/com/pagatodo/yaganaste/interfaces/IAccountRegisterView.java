package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAccountRegisterView extends IAccountView2 {

    public void userCreatedSuccess(String message);
    public void accountAvaliableAssigned(String result);
    public void accountConfirmed(String result);
}
