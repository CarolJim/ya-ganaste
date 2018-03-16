package com.pagatodo.yaganaste.ui_wallet.interactors;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.ui_wallet.interfaces.INewPaymentInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.INewPaymentPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by FranciscoManzo on 28/12/2017.
 */

public class NewPaymentInteractor implements INewPaymentInteractor, IRequestResult {

    INewPaymentPresenter mPresenter;
    private int typeDataFav;

    public NewPaymentInteractor(INewPaymentPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getCatalogosRecargarFromService() {

    }

    @Override
    public void getCatalogosFromDB(int mType) {
        List<Comercio> catalogos = new ArrayList<>();
        try {
            catalogos = new DatabaseManager().getComerciosByType(mType);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        List<Favoritos> catalogos = new ArrayList<>();
        try {
            catalogos = new DatabaseManager().getListFavoritosByIdComercio(id);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
