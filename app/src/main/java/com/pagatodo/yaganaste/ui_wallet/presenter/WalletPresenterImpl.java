package com.pagatodo.yaganaste.ui_wallet.presenter;

import com.pagatodo.yaganaste.ui_wallet.interactors.WalletInteractor;
import com.pagatodo.yaganaste.ui_wallet.interactors.WalletInteractorImpl;
import com.pagatodo.yaganaste.ui_wallet.views.WalletView;

/**
 * Created by icruz on 12/12/2017.
 */

public class WalletPresenterImpl implements WalletPresenter, WalletInteractor.OnWalletFinishedListener {

    private WalletView walletView;
    private WalletInteractor walletInteractor;

    public WalletPresenterImpl(WalletView walletView, WalletInteractorImpl walletInteractor) {
        this.walletView = walletView;
        this.walletInteractor = walletInteractor;
    }

    @Override
    public void getWalletsCards(){
        walletView.showProgress();
        walletInteractor.getWalletsCards(this);
    }

    @Override
    public void onDestroy() {
        walletView = null;
    }

    @Override
    public void onError() {
        if (walletView != null){
            walletView.setError();
            walletView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if (walletView != null){
            walletView.completed();
        }
    }
}
