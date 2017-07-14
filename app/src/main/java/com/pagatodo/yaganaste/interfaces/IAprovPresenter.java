package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAprovPresenter {

    void verifyActivationAprov(String codeActivation);

    void activationAprov(String codeActivation);

    void subscribePushNotification();
}
