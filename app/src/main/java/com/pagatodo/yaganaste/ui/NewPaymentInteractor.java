package com.pagatodo.yaganaste.ui;

import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;

import java.util.List;

/**
 * Created by FranciscoManzo on 28/12/2017.
 */

public class NewPaymentInteractor implements INewPaymentInteractor{

    INewPaymentPresenter mPresenter;
    CatalogsDbApi catalogsDbApi;

    public NewPaymentInteractor(INewPaymentPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void testToInteractor() {
        mPresenter.resToPresenter();
    }

    @Override
    public void getCatalogosRecargarFromService() {

    }

    @Override
    public void getCatalogosFromDB(int mType) {
        List<ComercioResponse> catalogos = catalogsDbApi.getComerciosList(mType);
        if (catalogos.size() > 0) {
            mPresenter.onSuccesDBObtenerCatalogos(catalogos);
        } else {
            mPresenter.onErrorService();
        }
    }
}
