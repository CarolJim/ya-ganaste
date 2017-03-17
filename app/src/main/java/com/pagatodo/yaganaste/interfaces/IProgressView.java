package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 20/02/2017.
 */

public interface IProgressView<T> {

    public void showProgress(String message);
    public void hideProgress();
    public void showError(T error);
}
