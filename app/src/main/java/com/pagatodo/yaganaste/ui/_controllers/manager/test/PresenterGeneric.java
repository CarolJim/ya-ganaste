package com.pagatodo.yaganaste.ui._controllers.manager.test;

import android.app.Activity;
import android.content.Context;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.net.ApiAdtvo;

/**
 * Created by Francisco Manzo on 20/06/2017.
 */

public class PresenterGeneric implements IPresenterGeneric{

    Activity mView;
    public PresenterGeneric(Activity mView) {
        this.mView = mView;
    }

    public void closeSession(Context mContext2){
        try {
            ApiAdtvo.cerrarSesion();
            mView.finish();
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

}
