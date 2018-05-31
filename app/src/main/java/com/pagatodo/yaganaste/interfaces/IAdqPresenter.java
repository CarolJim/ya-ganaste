package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.request.adq.SignatureData;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.TransaccionEMVDepositRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAdqPresenter {
    void validateDongle(String serial);

    void initTransaction(TransaccionEMVDepositRequest request, boolean signWithPin);

    void initCancelation(TransaccionEMVDepositRequest request, DataMovimientoAdq dataMovimientoAdq);

    void sendSignature(SignatureData signatureData);

    void sendTicket(String email, boolean applyAgent);
}
