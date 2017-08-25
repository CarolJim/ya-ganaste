package com.pagatodo.yaganaste.ui.payments.presenters.interfaces;

import com.pagatodo.yaganaste.interfaces.enums.WebService;

/**
 * Created by jmhernandez on 22/05/2017.
 */

public interface IPaymentsSuccessPresenter {
    void sendTicket(String mail, String idTransaction);

    void sendTicketEnvio(String mail, String idMovimiento);

    void onSuccess(WebService ws, Object success);

    void onError(WebService ws, Object error);
}
