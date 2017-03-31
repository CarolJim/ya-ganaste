package com.pagatodo.yaganaste.ui.maintabs.managers;

/**
 * @author Juan Guerra on 28/03/2017.
 */

public interface MovementsManager<T> {

    void onSuccesResponse(T response);
    void onFailed(int errorCode, int action, String error);
}
