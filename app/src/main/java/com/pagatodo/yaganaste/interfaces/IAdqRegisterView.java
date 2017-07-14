package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAdqRegisterView<E> extends IAccountAddressRegisterView<E> {
    void agentCreated(String message);
}
