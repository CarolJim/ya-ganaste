package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 22/03/2017.
 */

public interface IProgressView<T> extends ISessionExpired {

    //Mostrar Loader personalizado
    void showLoader(String message);

    //Mostrar Loader personalizado]
    void hideLoader();

    //Mostramos error personalido
    void showError(T error);


}
