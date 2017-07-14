package com.pagatodo.yaganaste.freja.general;

import com.pagatodo.yaganaste.freja.Errors;

/**
 * @author Juan Guerra on 30/03/2017.
 */

public interface ErrorFmcManager {

    void handleException(Exception e);

    void onError(Errors error);
}
