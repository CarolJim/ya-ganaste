package com.pagatodo.yaganaste.freja.reset.managers;

import com.pagatodo.yaganaste.freja.general.ErrorFmcManager;

/**
 * @author Juan Guerra on 30/03/2017.
 */

public interface ResetPinManager extends ErrorFmcManager {

    void setResetPinPolicy(int min, int max);

    void endResetPin();
}
