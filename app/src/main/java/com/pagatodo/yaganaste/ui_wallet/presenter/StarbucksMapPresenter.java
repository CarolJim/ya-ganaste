package com.pagatodo.yaganaste.ui_wallet.presenter;

import com.pagatodo.yaganaste.ui_wallet.interfaces.IStarbucksMapPresenter;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IStarbucksMapsView;

public class StarbucksMapPresenter implements IStarbucksMapPresenter {

    private IStarbucksMapsView view;

    public StarbucksMapPresenter(IStarbucksMapsView view) {
        this.view = view;
    }

    @Override
    public void getAllStores() {

    }

    @Override
    public void getStoresBySearch(String search) {

    }
}
