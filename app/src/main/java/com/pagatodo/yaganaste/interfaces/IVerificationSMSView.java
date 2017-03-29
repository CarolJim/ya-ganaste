package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.MessageValidation;

/**
 * Created by flima on 09/02/17.
 *
 * Interfaz que proporciona los métodos básicos para gestionar el flujo del Registro en la aplicación.
 */

public interface IVerificationSMSView extends IAccountView2{
    public void messageCreated(MessageValidation messageValidation);
    public void smsVerificationSuccess();
    public void smsVerificationFailed(String message);
}
