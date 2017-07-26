package com.pagatodo.yaganaste.ui.cupo.presenters.interfaces;

import com.pagatodo.yaganaste.data.DataSourceResult;

/**
 * Created by Dell on 26/07/2017.
 */

public interface IStatusRegisterCupo {

    public void doRequestStatusRegister();
    public void onObtainStatusSuccess(DataSourceResult dataSourceResult);
    public void onObtainStatusFailed(DataSourceResult dataSourceResult);
}
