package com.pagatodo.yaganaste.interfaces;

/**
 * Created by flima on 20/02/2017.
 */

public interface IAprovView<T> {

    void showErrorAprov(T error);

    void provisingCompleted();

    void subscribeNotificationSuccess();

}
