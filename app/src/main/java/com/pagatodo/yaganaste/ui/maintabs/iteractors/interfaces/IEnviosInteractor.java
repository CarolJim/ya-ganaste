package com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces;

import com.pagatodo.yaganaste.interfaces.enums.TransferType;

/**
 * Created by Jordan on 20/04/2017.
 */

public interface IEnviosInteractor {

    void validateForms(TransferType type, String number, String importe, String name, String concept, String reference, OnValidationFinishListener listener);

    interface OnValidationFinishListener {
        void onTypeError();

        void onNumberEmpty();

        void onNumberError();

        void onImporteEmpty();

        void onImporteError();

        void onNameEmpty();

        void onNameError();

        void onConceptEmpty();

        void onConceptError();

        void onReferenceEmpty();

        void onReferenceError();

        void onSuccess(Double monto);
    }
}
