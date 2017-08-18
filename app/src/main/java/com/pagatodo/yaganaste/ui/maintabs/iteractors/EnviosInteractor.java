package com.pagatodo.yaganaste.ui.maintabs.iteractors;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.ConsultarTitularCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarTitularCuentaResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.enums.TransferType;
import com.pagatodo.yaganaste.net.ApiTrans;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IEnviosInteractor;
import com.pagatodo.yaganaste.utils.ValidateForm;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_TITULAR_CUENTA;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

/**
 * Created by Jordan on 20/04/2017.
 */

public class EnviosInteractor implements IEnviosInteractor {
    double i = 0;
    OnValidationFinishListener listener;

    public EnviosInteractor(OnValidationFinishListener listener) {
        this.listener = listener;
    }

    @Override
    public void validateForms(TransferType type, String number, String importe,
                              String name, String concept, String reference) {

        if (type != null) {
            if (number == null || number.isEmpty()) {
                switch (type) {
                    case CABLE:
                        listener.onNumberCABLEEmpty();
                        return;
                    case NUMERO_TARJETA:
                        listener.onNumberCreditCardEmpty();
                        return;
                    case NUMERO_TELEFONO:
                        listener.onNumberCellPhoneEmpty();
                        return;
//                listener.onNumberEmpty();
//                return;
                }
            }

            switch (type) {
                case CABLE:
                    if (!ValidateForm.isValidCABLE(number)) {
                        listener.onNumberErrorCABLE();
                        return;
                    }
                    break;
                case NUMERO_TARJETA:
                    if (!ValidateForm.isValidCreditCard(number)) {
                        listener.onNumberErrorCreditCard();
                        return;
                    }
                    break;
                case NUMERO_TELEFONO:
                    if (!ValidateForm.isValidCellPhone(number)) {
                        listener.onNumberErrorCellPhone();
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

        if (reference.length() < 6) {
            listener.onReferenceShort();
            return;
        }

        if (reference.equals("000000")) {
            listener.onReferenceInvalid();
            return;
        }

        listener.onSuccess(i);
    }

    @Override
    public void getTitularName(String cuenta) {
        ConsultarTitularCuentaRequest request = new ConsultarTitularCuentaRequest();
        request.setCuenta(cuenta);
        try {
            ApiTrans.consultarTitularCuenta(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            listener.onError(CONSULTAR_TITULAR_CUENTA, App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void onSuccess(DataSourceResult result) {
        if (result.getWebService() == CONSULTAR_TITULAR_CUENTA) {
            ConsultarTitularCuentaResponse data = (ConsultarTitularCuentaResponse) result.getData();
            if (data.getAccion() == CODE_OK) {
                listener.onSuccess(CONSULTAR_TITULAR_CUENTA, data);
            } else {
                listener.onError(CONSULTAR_TITULAR_CUENTA, data.getMensaje());
            }
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        listener.onError(error.getWebService(), error.getData().toString());
    }
}
