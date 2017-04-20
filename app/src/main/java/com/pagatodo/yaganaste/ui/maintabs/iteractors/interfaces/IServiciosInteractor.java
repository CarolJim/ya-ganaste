package com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces;

/**
 * Created by Jordan on 19/04/2017.
 */

public interface IServiciosInteractor {
    interface OnValidationFinishListener{
        void onReferenciaError();
        void onReferenciaEmpty();
        void onImporteError();
        void onImporteEmpty();
        void onConceptEmpty();
        void onSuccess();
    }

    void validateForms(String referencia, String importe, String concepto);
}
