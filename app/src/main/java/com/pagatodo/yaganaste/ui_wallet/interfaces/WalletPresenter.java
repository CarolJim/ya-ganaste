package com.pagatodo.yaganaste.ui_wallet.interfaces;

/**
 * Created by icruz on 12/12/2017.
 */

public interface WalletPresenter {
    void getWalletsCards(boolean error);
    void updateBalance(int typeWallet);
    void getStatusAccount(String mTDC);
    void getInformacionAgente();

}
