package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 20/02/2017.
 */

public interface IAprovView<T> {

    public void showErrorAprov(T error);
    public void provisingCompleted();
    public void subscribeNotificationSuccess();

}
