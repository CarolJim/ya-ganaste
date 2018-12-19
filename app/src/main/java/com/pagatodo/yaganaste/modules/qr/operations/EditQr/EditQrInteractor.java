package com.pagatodo.yaganaste.modules.qr.operations.EditQr;

import com.pagatodo.yaganaste.modules.data.QrItems;

public class EditQrInteractor implements EditQrContracts.Interactor{

    private EditQrContracts.Listener listener;

    public EditQrInteractor(EditQrContracts.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onEditQr(QrItems item) {

    }
}
