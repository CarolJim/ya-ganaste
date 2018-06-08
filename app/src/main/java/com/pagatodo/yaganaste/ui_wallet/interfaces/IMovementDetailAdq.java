package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;

public interface IMovementDetailAdq {
    void getDetailMovement();

    void completeDetailMov(DataMovimientoAdq response);

    void onErrorTicket(Object data);
}
