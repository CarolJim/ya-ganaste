package com.pagatodo.yaganaste.ui_wallet.views;

/**
 * Created by icruz on 12/12/2017.
 */

public interface WalletView  extends MainWalletView{

    void setError();
    void completed(boolean error);
    void getSaldo();
}
