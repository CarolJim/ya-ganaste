package com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordan on 11/04/2017.
 */

public interface IPaymentsCarouselPresenter {

    void getCarouselItems();

    void getFavoriteCarouselItems();

    //ArrayList<CarouselItem> getCarouselArray();

    void onErrorService();

    void onEmptyListFavorites();

    //MovementsTab getCurrenTab();

    void onSuccessWSObtenerCatalogos(DataSourceResult result);

    void onSuccessWSFavorites(DataSourceResult result);

    void onSuccesDBObtenerCatalogos(List<ComercioResponse> comercios);

    void onSuccessDBFavorites(List<DataFavoritos> favoritos);
}
