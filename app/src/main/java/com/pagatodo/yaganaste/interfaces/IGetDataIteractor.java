package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 20/02/2017.
 *
 * Interfaz que proporciona los métodos básicos para la obtención y procesamiento de datos de un source local o remoto.
 */

public interface IGetDataIteractor<Args, T>{

    public void getDataFromFromWS(Args... args);
    public void getDataFromLocal(Args... args);
    public void saveLocalData(T data);
    public void updateLocalData(T data);
}
