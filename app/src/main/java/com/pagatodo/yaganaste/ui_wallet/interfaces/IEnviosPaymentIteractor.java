package com.pagatodo.yaganaste.ui_wallet.interfaces;

/**
 * Created by Armando Sandoval on 10/01/2018.
 */

public interface IEnviosPaymentIteractor {
    void getCatalogosRecargarFromService();

    void getCatalogosFromDB(int mType);

    void getFavoritesFromService(int typeDataFav);

    void getFavoritesFromDB(int id);
}
