package com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces;

import com.pagatodo.yaganaste.interfaces.enums.TransferType;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.interfaces.IRequestResult;

/**
 * Created by Jordan on 20/04/2017.
 */

public interface IEnviosInteractor extends IRequestResult{

    void validateForms(TransferType type, String number, int typeCard, String importe, String name, String concept, String reference);

    void getTitularName(String cuenta);

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

        void onReferenceShort();

        void onReferenceInvalid();

        void onNumberErrorCABLE();

        void onNumberErrorCreditCard();

        void onNumberErrorCellPhone();

        void onNumberCABLEEmpty();

        void onNumberCreditCardEmpty();

        void onNumberCellPhoneEmpty();

        void onSuccess(WebService ws, Object success);

        void onError(WebService ws, Object error);
    }
}
