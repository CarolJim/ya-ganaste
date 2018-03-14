package com.pagatodo.yaganaste.ui_wallet.interfaces;

/**
 * Created by icruz on 12/12/2017.
 */

public interface IWalletView extends IMainWalletView {


    void completed(boolean error);
    void getSaldo();
}
