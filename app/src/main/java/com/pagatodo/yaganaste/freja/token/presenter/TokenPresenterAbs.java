package com.pagatodo.yaganaste.freja.token.presenter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.token.iteractor.TokenIteractor;
import com.pagatodo.yaganaste.freja.token.iteractor.TokenIteractorImp;
import com.pagatodo.yaganaste.freja.token.manager.TokenManager;

/**
 * @author Juan Guerra on 11/04/2017.
 */

public class TokenPresenterAbs implements TokenPresenter, TokenManager {

    private static final String TAG = TokenPresenterAbs.class.getName();

    private TokenIteractor tokenIteractor;

    public TokenPresenterAbs(Context context) {
        this.tokenIteractor = new TokenIteractorImp(this);
        this.tokenIteractor.init(context);
    }

    @Override
    public boolean hasOnlineToken() {
        return tokenIteractor.hasOnLineToken();
    }

    @Override
    public boolean hasOfflineToken() {
        return tokenIteractor.hasOfflineToken();
    }

    @Nullable
    @Override
    public String getOnlineToken() {
        if (hasOnlineToken()) {
            return tokenIteractor.getOnlineToken();
        }
        return null;
    }

    @Nullable
    @Override
    public String getOfflineToken() {
        if (hasOfflineToken()) {
            return tokenIteractor.getOfflineToken();
        }
        return null;
    }

    @Override
    public void handleException(Exception e) {
        Log.i(TAG, e.toString());
    }

    @Override
    public void onError(Errors error) {
        //NO-OP
    }
}
