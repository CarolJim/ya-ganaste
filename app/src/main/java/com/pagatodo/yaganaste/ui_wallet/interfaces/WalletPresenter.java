package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;

/**
 * Created by icruz on 12/12/2017.
 */

public interface WalletPresenter {
    void getWalletsCards(boolean error);
    void updateBalance(ElementWallet item);
    void getStatusAccount(String mTDC);
    void getInformacionAgente();

}
