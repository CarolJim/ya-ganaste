package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;

public interface IDetailAdqView {

    void printTicket(DataMovimientoAdq ticket);

    void onError(String message);
}
