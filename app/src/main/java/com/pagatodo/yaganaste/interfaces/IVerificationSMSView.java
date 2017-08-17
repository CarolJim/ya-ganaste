package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.MessageValidation;

/**
 * Created by flima on 09/02/17.
 * <p>
 * Interfaz que proporciona los métodos básicos para gestionar el flujo del Registro en la aplicación.
 */

public interface IVerificationSMSView extends INavigationView {
    void messageCreated(MessageValidation messageValidation);

    void smsVerificationSuccess();

    void smsVerificationFailed(String message);

    void devicesAlreadyAssign(String message);

    void dataUpdated(String message);

}
