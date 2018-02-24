package com.pagatodo.yaganaste.ui_wallet.interactors;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.ui_wallet.interfaces.INewPaymentInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.INewPaymentPresenter;

import java.util.List;

/**
 * Created by FranciscoManzo on 28/12/2017.
 */

public class NewPaymentInteractor implements INewPaymentInteractor, IRequestResult {

    INewPaymentPresenter mPresenter;
    CatalogsDbApi catalogsDbApi;
    private int typeDataFav;

    public NewPaymentInteractor(INewPaymentPresenter mPresenter) {
        this.mPresenter = mPresenter;
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

    @Override
    public void getFavoritesFromService(int typeDataFav) {
        this.typeDataFav = typeDataFav;
        try {
            ApiAdtvo.consultarFavoritos(this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getFavoritesFromDB(int id) {
        List<DataFavoritos> catalogos = catalogsDbApi.getFavoritesList(id);
        mPresenter.onSuccessDBFavorites(catalogos);
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        switch (dataSourceResult.getWebService()) {
            case OBTENER_CATALOGOS:
               // mPresenter.onSuccessWSObtenerCatalogos(dataSourceResult);
                break;
            case OBTENER_FAVORITOS:
                mPresenter.onSuccessWSFavorites(dataSourceResult, typeDataFav);
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        mPresenter.onFail(error);
    }
}
