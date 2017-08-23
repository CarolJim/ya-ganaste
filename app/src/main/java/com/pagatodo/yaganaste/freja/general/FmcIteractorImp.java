package com.pagatodo.yaganaste.freja.general;

import android.content.Context;

import com.verisec.freja.mobile.core.FmcLogManager;
import com.verisec.freja.mobile.core.FmcManager;
import com.verisec.freja.mobile.core.configuration.FmcConfiguration;

/**
 * @author Juan Guerra on 30/03/2017.
 * @version 1.0
 */

public abstract class FmcIteractorImp implements FmcIteractor {

    protected FmcManager fmcManager;

    @Override
    public void init(Context context) {
        try {
            FmcLogManager.enableLoggingAll(true);
            FmcManager.setContext(context);
            this.fmcManager = FmcManager.getFmcManager();
        } catch (Exception e) {
            throwInitException(e);
        }
    }

    @Override
    public void init(Context context, long connectionTimeout, long readTimeout) {
        try {
            FmcManager.setContext(context);
            fmcManager = FmcManager.getFmcManager();

            FmcConfiguration fmcConfiguration = fmcManager.getFmcConfiguration();
            fmcConfiguration.setConnectionTimeout(connectionTimeout);
            fmcConfiguration.setReadTimeout(readTimeout);
            fmcManager.updateConfiguration(fmcConfiguration);
        } catch (Exception e) {
            throwInitException(e);
        }
    }

}