package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.request.adq.CancelaTransaccionDepositoEmvRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.EnviarTicketCompraRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.FirmaDeVoucherRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.TransaccionEMVDepositRequest;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAdqIteractor {

    void registerDongle();

    void initPayment(TransaccionEMVDepositRequest request, boolean signWithPin);

    void initCancelPayment(CancelaTransaccionDepositoEmvRequest request);

    void initReversePayment(CancelaTransaccionDepositoEmvRequest request);

    void initConsult(TransaccionEMVDepositRequest request);

    void sendSignalVoucher(FirmaDeVoucherRequest request);

    void sendTicket(EnviarTicketCompraRequest request, boolean applyAgent);

    void enviarTicketCompraShare(EnviarTicketCompraRequest enviarTicketCompraRequest);
}

