package com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;

import java.util.List;


public interface IPaymentsCarouselPresenter {

    void getdatabank(String bin,String cob);

    void getCarouselItems();

    void getFavoriteCarouselItems();

    //ArrayList<CarouselItem> getCarouselArray();

    void onErrorService();

    void onEmptyListFavorites();

    //MovementsTab getCurrenTab();

    void onSuccessWSObtenerCatalogos(DataSourceResult result);

    void onSuccessWSFavorites(DataSourceResult result);

    void onSuccessWSBankBin(DataSourceResult result);

    void onSuccesDBObtenerCatalogos(List<Comercio> comercios);

    void onSuccessDBFavorites(List<Favoritos> favoritos);
}
