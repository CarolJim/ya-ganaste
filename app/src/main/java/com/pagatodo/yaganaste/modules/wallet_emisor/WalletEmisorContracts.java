package com.pagatodo.yaganaste.modules.wallet_emisor;

import com.pagatodo.yaganaste.interfaces.IRequestResult;

class WalletEmisorContracts {

    interface Listener{
        void showLoad();
        void hideLoad();
        void onSuccessTemporaryBlock();
        void onErrorTemporaryBlock();
    }

    interface Interactor extends IRequestResult {
        void TemporaryBlock();
    }

    interface Router{
        void onShowActivatePhysicalCard();
        void onShowGeneratePIN();
        void onshowAccountStatus();
        void onShowMyVirtualCardAccount();
        void onShowMyChangeNip();
        void onShowBlockCard();
        void onShowTemporaryBlock();
    }
}
