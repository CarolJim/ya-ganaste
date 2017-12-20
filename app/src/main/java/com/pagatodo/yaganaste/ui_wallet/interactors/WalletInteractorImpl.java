package com.pagatodo.yaganaste.ui_wallet.interactors;


import android.os.Handler;

/**
 * Created by icruz on 12/12/2017.
 */

public class WalletInteractorImpl implements WalletInteractor {

    @Override
    public void getWalletsCards(final OnWalletFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.onSuccess();
            }
        },5000);
        //listener.onError();
    }
}
