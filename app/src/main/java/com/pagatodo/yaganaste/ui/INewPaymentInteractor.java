package com.pagatodo.yaganaste.ui;

/**
 * Created by FranciscoManzo on 28/12/2017.
 */

public interface INewPaymentInteractor {
    void testToInteractor();

    void getCatalogosRecargarFromService();

    void getCatalogosFromDB(int mType);

    void getFavoritesFromService(int typeDataFav);

    void getFavoritesFromDB(int id);
}
