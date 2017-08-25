package com.pagatodo.yaganaste.ui.payments.interactors.interfaces;

import com.pagatodo.yaganaste.net.IRequestResult;

/**
 * Created by jmhernandez on 22/05/2017.
 */

public interface IPaymentsSuccessInteractor extends IRequestResult {
    void sendTicket(String mail, String idTransaction);

    void sendTicketEnvio(String mail, String idMovimiento);
}
