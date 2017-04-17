package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActivacionAprovSofttokenRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.VerificarActivacionAprovSofttokenRequest;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAprovIteractor<T>{
    public void verifyActivationAprov(VerificarActivacionAprovSofttokenRequest request);
    public void activationAprov(ActivacionAprovSofttokenRequest request);
}
