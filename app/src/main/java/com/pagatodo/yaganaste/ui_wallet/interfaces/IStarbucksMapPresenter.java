package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.response.starbucks.StarbucksStores;

import java.util.List;

public interface IStarbucksMapPresenter {

    void getAllStores(double latitude, double longitude);

    void getStoresBySearch(String search, double latitude, double longitude);

    void onSearchFailed();

    void onSearchEmpty();

    void onSearchSuccesfull(List<StarbucksStores> stores);
}
