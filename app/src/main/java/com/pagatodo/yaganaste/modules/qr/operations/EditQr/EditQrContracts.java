package com.pagatodo.yaganaste.modules.qr.operations.EditQr;

import com.pagatodo.yaganaste.modules.data.QrItems;

public class EditQrContracts {

    interface Listener{
        void onSuccessEdit();
        void onErrorEdit();
    }

    interface Interactor{
        void onEditQr(QrItems item);
    }
}
