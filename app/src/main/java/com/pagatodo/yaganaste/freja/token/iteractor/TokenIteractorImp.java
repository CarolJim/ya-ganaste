package com.pagatodo.yaganaste.freja.token.iteractor;

import android.content.Context;

import com.pagatodo.yaganaste.freja.general.FmcIteractorImp;
import com.pagatodo.yaganaste.freja.token.manager.TokenManager;
import com.verisec.freja.mobile.core.configuration.FmcConfiguration;
import com.verisec.freja.mobile.core.exceptions.FmcInternalException;

/**
 * @author Juan Guerra on 11/04/2017.
 */

public class TokenIteractorImp extends FmcIteractorImp implements TokenIteractor {

    private FmcConfiguration fmcConfiguration;
    private TokenManager tokenManager;

    public TokenIteractorImp(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public void init(Context context) {
        super.init(context);
        getCofiguration();
    }

    @Override
    public void init(Context context, long connectionTimeout, long readTimeout) {
        super.init(context, connectionTimeout, readTimeout);
        getCofiguration();
    }

    private void getCofiguration() {
        try {
            this.fmcConfiguration = fmcManager.getFmcConfiguration();
        } catch (FmcInternalException e) {
            throwInitException(e);
        }
    }

    @Override
    public boolean hasOnLineToken() {
        return fmcConfiguration.existsOnlineToken();
    }

    @Override
    public boolean hasOfflineToken() {
        return fmcConfiguration.existsOfflineToken();
    }

    @Override
    public String getOnlineToken() {
        return fmcConfiguration.getOnlineToken().getSerialNumber();
    }

    @Override
    public String getOfflineToken() {
        return fmcConfiguration.getOfflineToken().getSerialNumber();
    }

    @Override
    public void throwInitException(Exception e) {
        tokenManager.handleException(e);
    }
}
