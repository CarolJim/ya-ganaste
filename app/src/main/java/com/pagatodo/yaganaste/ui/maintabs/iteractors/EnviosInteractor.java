package com.pagatodo.yaganaste.ui.maintabs.iteractors;

import com.pagatodo.yaganaste.interfaces.enums.TransferType;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IEnviosInteractor;
import com.pagatodo.yaganaste.utils.ValidateForm;

/**
 * Created by Jordan on 20/04/2017.
 */

public class EnviosInteractor implements IEnviosInteractor {
    double i = 0;

    @Override
    public void validateForms(TransferType type, String number, String importe,
                              String name, String concept, String reference,
                              OnValidationFinishListener listener) {

        if (type != null) {
            if (number == null || number.isEmpty()) {
                listener.onNumberEmpty();
                return;
            }

            switch (type) {
                case CABLE:
                    if (!ValidateForm.isValidCABLE(number)) {
                        listener.onNumberError();
                        return;
                    }
                    break;
                case NUMERO_TARJETA:
                    if (!ValidateForm.isValidCreditCard(number)) {
                        listener.onNumberError();
                        return;
                    }
                    break;
                case NUMERO_TELEFONO:
                    if (!ValidateForm.isValidCellPhone(number)) {
                        listener.onNumberError();
                        return;
                    }
                    break;
            }
        } else {
            listener.onTypeError();
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

        if (name == null || name.isEmpty()) {
            listener.onNameEmpty();
            return;
        }

        if (concept == null || concept.isEmpty()) {
            listener.onConceptEmpty();
            return;
        }

        if (reference == null || reference.isEmpty()) {
            listener.onReferenceEmpty();
            return;
        }

        listener.onSuccess();
    }
}
