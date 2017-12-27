package com.pagatodo.yaganaste.ui_wallet.presenter;

import com.pagatodo.yaganaste.ui_wallet.interactors.WalletInteractor;
import com.pagatodo.yaganaste.ui_wallet.interactors.WalletInteractorImpl;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WlletNotifaction;
import com.pagatodo.yaganaste.ui_wallet.views.WalletView;

/**
 * Created by icruz on 12/12/2017.
 */

public class WalletPresenterImpl implements WalletPresenter, WlletNotifaction {

    private WalletView walletView;
    private WalletInteractor walletInteractor;

    public WalletPresenterImpl(WalletView walletView) {
        this.walletView = walletView;
        this.walletInteractor = new WalletInteractorImpl();
    }

    public WalletPresenterImpl(){

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
    public void getMovementsAdq() {
        walletView.showProgress();
    }

    @Override
    public void onSuccess() {
        if (walletView != null){
            walletView.completed();
        }
    }

    @Override
    public void onFailed(int errorCode, int action, String error) {
        if (walletView != null){
            walletView.setError();
            walletView.hideProgress();
        }
    }
}
