package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.response.starbucks.StarbucksStores;

import java.util.List;

public interface IStarbucksMapsView {

    void showLoader(String text);

    void hideLoader();

    void showError(String error);

    void setStoresInMap(List<StarbucksStores> stores);
}
