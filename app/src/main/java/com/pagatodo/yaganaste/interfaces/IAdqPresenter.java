package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.request.adq.SignatureData;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.TransaccionEMVDepositRequest;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAdqPresenter {
    public void validateDongle(String serial);
    public void initTransaction(TransaccionEMVDepositRequest request);
    public void sendSignature(SignatureData signatureData);
    public void sendTicket(String email);
}
