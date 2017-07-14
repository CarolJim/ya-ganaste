package com.pagatodo.yaganaste.freja.token.iteractor;

import com.pagatodo.yaganaste.freja.general.FmcIteractor;

/**
 * @author Juan Guerra on 11/04/2017.
 */

public interface TokenIteractor extends FmcIteractor {
    boolean hasOnLineToken();

    boolean hasOfflineToken();

    String getOnlineToken();

    String getOfflineToken();
}
