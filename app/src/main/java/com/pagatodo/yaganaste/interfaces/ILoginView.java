package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 09/02/17.
 *
 * Interfaz que proporciona los métodos básicos para gestionar el flujo del Login en la aplicación.
 */

public interface ILoginView extends IProgressView{
    public void loginSucced();
    public void recoveryPasswordSucced();
}
