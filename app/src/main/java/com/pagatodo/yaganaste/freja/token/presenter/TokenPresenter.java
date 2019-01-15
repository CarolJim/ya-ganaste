package com.pagatodo.yaganaste.freja.token.presenter;

import androidx.annotation.Nullable;

/**
 * @author Juan Guerra on 11/04/2017.
 */

public interface TokenPresenter {
    boolean hasOnlineToken();

    boolean hasOfflineToken();

    @Nullable
    String getOnlineToken();

    @Nullable
    String getOfflineToken();
}
