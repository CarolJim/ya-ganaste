package com.pagatodo.yaganaste.ui_wallet.views;

/**
 * Created by icruz on 12/12/2017.
 */

public interface WalletView  {
    void showProgress();

    void hideProgress();

    void setError();

    void completed();
}
