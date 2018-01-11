package com.pagatodo.yaganaste.ui_wallet.presenter;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;

/**
 * Created by FranciscoManzo on 10/01/2018.
 */

public interface IPresenterPaymentFragment {
    ComercioResponse getComercioById(long idComercio);

    void validateFieldsCarrier(String referencia, String serviceImport, String concepto, int longitudReferencia);
}
