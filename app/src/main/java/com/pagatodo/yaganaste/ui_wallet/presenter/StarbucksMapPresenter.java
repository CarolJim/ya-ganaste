package com.pagatodo.yaganaste.ui_wallet.presenter;

import com.facebook.stetho.common.StringUtil;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.StarbucksStoresRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.starbucks.StarbucksStores;
import com.pagatodo.yaganaste.ui_wallet.interactors.StarbucksMapIteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IStarbucksMapPresenter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IStarbucksMapsView;
import com.pagatodo.yaganaste.utils.StringUtils;

import java.util.List;

public class StarbucksMapPresenter implements IStarbucksMapPresenter {

    private IStarbucksMapsView view;
    private StarbucksMapIteractor iteractor;

    public StarbucksMapPresenter(IStarbucksMapsView view) {
        this.view = view;
        iteractor = new StarbucksMapIteractor(this);
    }

    @Override
    public void getAllStores(double lat, double lon) {
        view.showLoader(App.getContext().getString(R.string.searching_stores));
        StarbucksStoresRequest starbucksStoresRequest = new StarbucksStoresRequest("", lat, lat, lon, lon);
        iteractor.searchStores(starbucksStoresRequest);
    }

    @Override
    public void getStoresBySearch(String search, double lat, double lon) {
        view.showLoader(App.getContext().getString(R.string.searching_stores));
        StarbucksStoresRequest starbucksStoresRequest = new StarbucksStoresRequest(search, lat, lat, lon, lon);
        iteractor.searchStores(starbucksStoresRequest);
    }

    @Override
    public void onSearchFailed() {
        view.hideLoader();
        view.showError(App.getContext().getString(R.string.email_report_error_text));
    }

    @Override
    public void onSearchEmpty() {
        view.hideLoader();
        view.showError(App.getContext().getString(R.string.searching_stores_empty));
    }

    @Override
    public void onSearchSuccesfull(List<StarbucksStores> stores) {
        view.hideLoader();
        view.setStoresInMap(stores);
    }
}
