package com.pagatodo.yaganaste.ui_wallet.presenter;

import android.content.Context;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.ui.maintabs.fragments.EnviosFromFragmentNewVersion;
import com.pagatodo.yaganaste.ui_wallet.interactors.EnviosPaymentIteractor;
import com.pagatodo.yaganaste.ui_wallet.interactors.IEnviosPaymentIteractor;

import java.util.List;

/**
 * Created by Armando Sandoval on 10/01/2018.
 */

public class EnviosPaymentPresenter implements  IEnviosPaymentPresenter{

    EnviosFromFragmentNewVersion mView;
    IEnviosPaymentIteractor mInteractor;
    Context mContext;
    private CatalogsDbApi api;
    private int typeData;
    private int typeDataFav;


    public EnviosPaymentPresenter(EnviosFromFragmentNewVersion mView, Context context) {
        this.mView = mView;
        mInteractor = new EnviosPaymentIteractor(this);
        this.mContext = context;
        this.api = new CatalogsDbApi(context);
    }



    @Override
    public void getCarriersItems(int typeReload) {

    }

    @Override
    public void onSuccesDBObtenerCatalogos(List<ComercioResponse> catalogos) {

    }

    @Override
    public void onErrorService() {

    }

    @Override
    public void getFavoritesItems(int typeReload) {

    }

    @Override
    public void onSuccessWSFavorites(DataSourceResult dataSourceResult, int typeDataFav) {

    }

    @Override
    public void onSuccessDBFavorites(List<DataFavoritos> catalogos) {

    }

    @Override
    public void sendChoiceCarrier(ComercioResponse position, int mType) {

    }

    @Override
    public void sendChoiceFavorite(DataFavoritos dataFavoritos, int mType) {

    }

    @Override
    public void onFail(DataSourceResult error) {

    }
}
