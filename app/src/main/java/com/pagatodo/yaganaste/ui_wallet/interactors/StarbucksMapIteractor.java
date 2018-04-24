package com.pagatodo.yaganaste.ui_wallet.interactors;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.StarbucksStoresRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.starbucks.StarbucksStoresResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.net.ApiStarbucks;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IStarbucksMapIteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IStarbucksMapPresenter;

public class StarbucksMapIteractor implements IRequestResult, IStarbucksMapIteractor {

    private IStarbucksMapPresenter listener;

    public StarbucksMapIteractor(IStarbucksMapPresenter listener) {
        this.listener = listener;
    }

    @Override
    public void searchStores(StarbucksStoresRequest request) {
        try {
            ApiStarbucks.searchStores(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            listener.onSearchFailed();
        }
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        StarbucksStoresResponse response = (StarbucksStoresResponse) dataSourceResult.getData();
        if (response != null && response.getCodigo() == 0) {
            if (!response.getStores().isEmpty()) {
                listener.onSearchSuccesfull(response.getStores());
            } else {
                listener.onSearchEmpty();
            }
        } else {
            listener.onSearchFailed();
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        listener.onSearchFailed();
    }
}
