package com.pagatodo.yaganaste.ui.maintabs.managers;

/**
 * Created by Jordan on 19/05/2017.
 */

public interface DepositsManager {

    void onTapButton();
    void onError();
    void onSuccess();
    void showErrorMessage(String message);
    void onBtnBackPress();
}
