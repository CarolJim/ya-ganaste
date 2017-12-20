package com.pagatodo.yaganaste.ui_wallet.interactors;

/**
 * Created by icruz on 12/12/2017.
 */

public interface WalletInteractor {
    public interface OnWalletFinishedListener {
        void onError();
        void onSuccess();
    }

    void getWalletsCards(OnWalletFinishedListener listener);
}
