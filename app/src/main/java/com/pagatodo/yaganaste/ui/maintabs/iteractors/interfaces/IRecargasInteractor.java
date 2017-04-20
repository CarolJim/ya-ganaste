package com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces;

/**
 * Created by Jordan on 19/04/2017.
 */

public interface IRecargasInteractor {

    interface OnValidationFinishListener{
        void onMontoError();
        void onNumberError();
        void onNumberEmpty();
        void onSuccess();
    }

    void validateForms(String number, Double monto, OnValidationFinishListener listener);
}
