package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.ui_wallet.patterns.interfaces.Presenter;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet;

/**
 * Created by icruz on 12/12/2017.
 */

public interface  WalletPresenter extends Presenter{
    void getWalletsCards(boolean error);
    void updateBalance(ElementWallet item);
    void getStatusAccount(String mTDC);
    void getStatusDocuments();
    void getInfoComercio(String folio);
    //void getInformacionAgente();
}
