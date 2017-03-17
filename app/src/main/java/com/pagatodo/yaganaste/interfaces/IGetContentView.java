package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 20/02/2017.
 *
 * Interfaz que proporciona los métodos para mostrar información de algun data source en vista.
 */

public interface IGetContentView<T> extends IProgressView {

    public void fillContent(T content);

}
