package com.pagatodo.yaganaste.modules.wallet_emisor.PaymentToQR.operations.EditQr;

import com.pagatodo.yaganaste.modules.data.QrItems;

public class EditQrContracts {

    interface Listener{
        void onSuccessEdit();
        void onErrorEdit(String msjError);
    }

    interface Interactor{
        void onEditQr(QrItems item);
    }
}
