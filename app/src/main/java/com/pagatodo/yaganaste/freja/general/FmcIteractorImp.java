package com.pagatodo.yaganaste.freja.general;

import android.content.Context;

import com.pagatodo.yaganaste.App;
import com.verisec.freja.mobile.core.FmcLogManager;
import com.verisec.freja.mobile.core.FmcManager;
import com.verisec.freja.mobile.core.configuration.FmcConfiguration;

import static com.pagatodo.yaganaste.utils.Recursos.DEBUG;

/**
 * @author Juan Guerra on 30/03/2017.
 * @version 1.0
 */

public abstract class FmcIteractorImp implements FmcIteractor {

    protected static FmcManager fmcManager;

    @Override
    public void init(Context context) {
        try {
            if (fmcManager == null) {
                FmcLogManager.enableLoggingAll(DEBUG);
                FmcManager.setContext(App.getInstance());
                this.fmcManager = FmcManager.getFmcManager();
            }
        } catch (Exception e) {
            throwInitException(e);
        }
    }

    @Override
    public void init(Context context, long connectionTimeout, long readTimeout) {
        try {
            if (fmcManager == null) {
                FmcLogManager.enableLoggingAll(DEBUG);
                FmcManager.setContext(App.getInstance());
                this.fmcManager = FmcManager.getFmcManager();
            }
            FmcConfiguration fmcConfiguration = fmcManager.getFmcConfiguration();
            fmcConfiguration.setConnectionTimeout(connectionTimeout);
            fmcConfiguration.setReadTimeout(readTimeout);
            fmcManager.updateConfiguration(fmcConfiguration);
        } catch (Exception e) {
            throwInitException(e);
        }
    }

}