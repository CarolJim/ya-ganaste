package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

/**
 * Created by flima on 22/03/2017.
 */

public interface IProgressView<T>  extends ISessionExpired{

    //Mostrar Loader personalizado
    public void showLoader(String message);
    //Mostrar Loader personalizado]
    public void hideLoader();
    //Mostramos error personalido
    public void showError(T error);


}
