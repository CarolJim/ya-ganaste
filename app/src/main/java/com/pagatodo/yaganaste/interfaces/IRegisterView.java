package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 09/02/17.
 *
 * Interfaz que proporciona los métodos básicos para gestionar el flujo del Registro en la aplicación.
 */

public interface IRegisterView extends IProgressView{
    public void registerSucced();
    public void smsVerificationSuccess();
    public void smsVerificationFailed();
}
