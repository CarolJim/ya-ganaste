package com.pagatodo.yaganaste.ui.preferuser.iteractors;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserIteractor;
import com.pagatodo.yaganaste.ui.preferuser.presenters.PreferUserPresenter;

/**
 * Created by Francisco Manzo on 08/06/2017.
 */

public class PreferUserIteractor implements IPreferUserIteractor, IRequestResult {

    PreferUserPresenter preferUserPresenter;

    public PreferUserIteractor(PreferUserPresenter preferUserPresenter) {
        this.preferUserPresenter = preferUserPresenter;
    }

    @Override
    public void testToastIteractor() {
        preferUserPresenter.testToastSucess();
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {

    }

    @Override
    public void onFailed(DataSourceResult error) {

    }
}
