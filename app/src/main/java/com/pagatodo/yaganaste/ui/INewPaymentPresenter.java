package com.pagatodo.yaganaste.ui;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;

import java.util.List;

/**
 * Created by FranciscoManzo on 28/12/2017.
 */

public interface INewPaymentPresenter {
    void getCarriersItems(int typeReload);

    void resToPresenter();

    void onSuccesDBObtenerCatalogos(List<ComercioResponse> catalogos);

    void onErrorService();

    void getFavoritesItems(int typeReload);

    void onSuccessWSFavorites(DataSourceResult dataSourceResult);

    void onSuccessDBFavorites(List<DataFavoritos> catalogos);
}
