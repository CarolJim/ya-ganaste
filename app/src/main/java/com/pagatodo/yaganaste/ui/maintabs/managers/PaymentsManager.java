package com.pagatodo.yaganaste.ui.maintabs.managers;

/**
 * Created by Jordan on 18/04/2017.
 */

public interface PaymentsManager {
    void showError();

    void onError(String error);

    void onSuccess(Double importe);
}
