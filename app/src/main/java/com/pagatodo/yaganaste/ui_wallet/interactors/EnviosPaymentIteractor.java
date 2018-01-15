package com.pagatodo.yaganaste.ui_wallet.interactors;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.ui_wallet.presenter.IEnviosPaymentPresenter;

/**
 * Created by Armando Sandoval on 10/01/2018.
 */

public class EnviosPaymentIteractor  implements IEnviosPaymentIteractor, IRequestResult {

    IEnviosPaymentPresenter mPresenter;
    CatalogsDbApi catalogsDbApi;
    private int typeDataFav;

    public EnviosPaymentIteractor(IEnviosPaymentPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getCatalogosRecargarFromService() {

    }

    @Override
    public void getCatalogosFromDB(int mType) {

    }

    @Override
    public void getFavoritesFromService(int typeDataFav) {

    }

    @Override
    public void getFavoritesFromDB(int id) {

    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {

    }

    @Override
    public void onFailed(DataSourceResult error) {

    }
}