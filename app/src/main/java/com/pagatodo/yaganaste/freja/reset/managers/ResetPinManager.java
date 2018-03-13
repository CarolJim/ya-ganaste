package com.pagatodo.yaganaste.freja.reset.managers;

import com.pagatodo.yaganaste.freja.general.ErrorFmcManager;
import com.pagatodo.yaganaste.interfaces.IRequestResult;

/**
 * @author Juan Guerra on 30/03/2017.
 */

public interface ResetPinManager extends ErrorFmcManager, IRequestResult {

    void setResetCode(String rpc);

    void setResetPinPolicy(int min, int max);

    void endResetPin();
}
