package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;

import java.util.List;

/**
 * Created by FranciscoManzo on 28/12/2017.
 */

public interface INewPaymentPresenter {
    void getCarriersItems(int typeReload);

    void onSuccesDBObtenerCatalogos(List<Comercio> catalogos);

    void onErrorService();

    void getFavoritesItems(int typeReload);

    void onSuccessWSFavorites(DataSourceResult dataSourceResult, int typeDataFav);

    void onSuccessDBFavorites(List<Favoritos> catalogos);

    void sendChoiceCarrier(Comercio position, int mType);

    void sendChoiceFavorite(Favoritos favoritos, int mType);

    void onFail(DataSourceResult error);
}
