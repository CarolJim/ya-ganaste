package com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerCatalogosResponse;

/**
 * Created by Jordan on 11/04/2017.
 */

public interface IPaymentsCarouselIteractor {
    void getdatabank(String pbusqueda,String cob);

    void getCatalogosFromService();

    void getCatalogosFromDB(int tabID);

    void getFavoritesFromService();

    void getFavoritesFromDB(int tabID);

   // ObtenerCatalogosResponse getCatalogos();
}
