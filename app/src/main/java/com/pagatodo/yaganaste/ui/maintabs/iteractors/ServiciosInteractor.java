package com.pagatodo.yaganaste.ui.maintabs.iteractors;

import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IServiciosInteractor;

/**
 * Created by Jordan on 19/04/2017.
 */

public class ServiciosInteractor implements IServiciosInteractor {
    double i = 0;

    @Override
    public void validateForms(String referencia, String importe, String concepto, int longitudReferencia, OnValidationFinishListener listener) {

        if (referencia == null || referencia.isEmpty()) {
            listener.onReferenciaEmpty();
            return;
        }

        if (referencia.length() < longitudReferencia) {


            listener.onReferenciaError();
            return;
        }

        if (importe == null || importe.isEmpty()) {
            listener.onImporteEmpty();
            return;
        } else {
            importe = importe.replace("$", "").replace(",", "");
            try {
                i = Double.valueOf(importe);
            } catch (Exception e) {
                e.printStackTrace();
                listener.onImporteError();
                return;
            }
        }

        if (i < 1) {
            listener.onImporteError();
            return;
        }

        if (concepto == null || concepto.isEmpty()) {
            listener.onConceptEmpty();
            return;
        }

        listener.onSuccess(i);

    }
}
