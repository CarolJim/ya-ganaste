package com.pagatodo.yaganaste.ui.maintabs.iteractors;

import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IRecargasInteractor;
import com.pagatodo.yaganaste.utils.ValidateForm;

/**
 * Created by Jordan on 19/04/2017.
 */

public class RecargasInteractor implements IRecargasInteractor {


    @Override
    public void validateForms(String number, Double monto, OnValidationFinishListener listener) {
        if(number.isEmpty() || number.equals("")){
            listener.onNumberEmpty();
            return;
        }

        if(!ValidateForm.isValidCellPhone(number)){
           listener.onNumberError();
            return;
        }

        if(monto == null || monto == 0.0){
            listener.onMontoError();
            return;
        }

        listener.onSuccess();
    }
}
