package com.pagatodo.yaganaste.freja.provisioning.manager;

import com.pagatodo.yaganaste.freja.Errors;

/**
 * @author Juan Guerra on 31/03/2017.
 */

public interface ExceptionCallback {
    void onError(Errors error);
}
