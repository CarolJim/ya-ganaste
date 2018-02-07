package com.pagatodo.yaganaste.ui_wallet.presenter;

/**
 * Created by icruz on 12/12/2017.
 */

public interface WalletPresenter {
    void getWalletsCards(boolean error);
    void updateBalance();
    void onDestroy();
}
