package com.pagatodo.yaganaste.freja.change.managers;

import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.general.ErrorFmcManager;

/**
 * @author Juan Guerra on 30/03/2017.
 */

public interface ChangePinManager extends ErrorFmcManager{

    void setChangePinPolicy(int min, int max);
    void endChangePin();
}
