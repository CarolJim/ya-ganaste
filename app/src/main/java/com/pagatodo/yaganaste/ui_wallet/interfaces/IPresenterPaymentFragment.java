package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.room_db.entities.Comercio;

/**
 * Created by FranciscoManzo on 10/01/2018.
 */

public interface IPresenterPaymentFragment {
    Comercio getComercioById(int idComercio);

    void validateFieldsCarrier(String referencia, String serviceImport, String concepto, int longitudReferencia);
}
