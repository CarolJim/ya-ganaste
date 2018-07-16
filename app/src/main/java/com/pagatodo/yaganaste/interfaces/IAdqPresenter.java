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

    void initConsultBalance(TransaccionEMVDepositRequest request);

    void initCancelation(TransaccionEMVDepositRequest request, DataMovimientoAdq dataMovimientoAdq);

    void initReverseTransaction(TransaccionEMVDepositRequest request, int typeReverse);

    void sendSignature(SignatureData signatureData);

    void sendTicket(String email, boolean applyAgent);

    void sendTicket(String idTransicion, String name, String email, boolean applyAgente);
}
