package com.pagatodo.yaganaste.ui.maintabs.iteractors;

import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IRecargasInteractor;
import com.pagatodo.yaganaste.utils.ValidateForm;

/**
 * Created by Jordan on 19/04/2017.
 */

public class RecargasInteractor implements IRecargasInteractor {


    @Override
    public void validateForms(String number, Double monto, int longitudReferencia, boolean isIAVE, OnValidationFinishListener listener) {
        if (number == null || number.isEmpty()) {
            listener.onNumberEmpty();
            return;
        }


        if (isIAVE && longitudReferencia != 0 && number.length() < longitudReferencia) {
            listener.onNumberError();
            return;
        }

        if (!isIAVE && !ValidateForm.isValidCellPhone(number)) {
            listener.onNumberError();
            return;
        }


        if (monto == null || monto == 0.0) {
            listener.onMontoError();
            return;
        }

        listener.onSuccess(monto);
    }
}
