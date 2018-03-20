package com.pagatodo.yaganaste.ui_wallet.presenter;

import android.content.Context;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.ui.maintabs.fragments.EnviosFromFragmentNewVersion;
import com.pagatodo.yaganaste.ui_wallet.interactors.EnviosPaymentIteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IEnviosPaymentIteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IEnviosPaymentPresenter;

import java.util.List;

/**
 * Created by Armando Sandoval on 10/01/2018.
 */

public class EnviosPaymentPresenter implements IEnviosPaymentPresenter {

    EnviosFromFragmentNewVersion mView;
    IEnviosPaymentIteractor mInteractor;
    Context mContext;
    private int typeData;
    private int typeDataFav;


    public EnviosPaymentPresenter(EnviosFromFragmentNewVersion mView, Context context) {
        this.mView = mView;
        mInteractor = new EnviosPaymentIteractor(this);
        this.mContext = context;
    }


    @Override
    public void getCarriersItems(int typeReload) {

    }

    @Override
    public void onSuccesDBObtenerCatalogos(List<Comercio> catalogos) {
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
    public void onSuccessDBFavorites(List<Favoritos> catalogos) {

    }

    @Override
    public void sendChoiceCarrier(Comercio position, int mType) {

    }

    @Override
    public void sendChoiceFavorite(Favoritos favoritos, int mType) {

    }

    @Override
    public void onFail(DataSourceResult error) {

    }
}
