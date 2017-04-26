package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.request.adq.EnviarTicketCompraRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.FirmaDeVoucherRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.LoginAdqRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.TransaccionEMVDepositRequest;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAdqIteractor {
    public void loginAdq();
    public void registerDongle();
    public void initPayment(TransaccionEMVDepositRequest request);
    public void sendSignalVoucher(FirmaDeVoucherRequest request);
    public void sendTicket(EnviarTicketCompraRequest request);
}

