package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.MessageValidation;

/**
 * Created by flima on 09/02/17.
 *
 * Interfaz que proporciona los métodos básicos para gestionar el flujo del Registro en la aplicación.
 */

public interface IVerificationSMSView extends INavigationView {
    public void messageCreated(MessageValidation messageValidation);
    public void smsVerificationSuccess();
    public void smsVerificationFailed(String message);
    public void devicesAlreadyAssign(String message);
    public void provisingCompleted();
    public void verifyActivationProvisingFailed(String message);
    public void activationProvisingFailed(String message);
    public void dataUpdated(String message);

}
