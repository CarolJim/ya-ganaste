package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 17/02/2017.
 *
 * Interfaz que proporciona los métodos básicos para la obtención y procesamiento de datos de un source local o remoto, los
 * cuales se comunicaran con el iteractor.
 */

public interface IGetDataPresenter<Args, Error> {

    public void getData(Args... params);
    public void processData(Args object);
    public void showError(Args params, Error error);

}
