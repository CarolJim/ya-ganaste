package com.pagatodo.yaganaste.modules.wallet_emisor;

class WalletEmisorContracts {
    interface Router{
        void onShowActivatePhysicalCard();
        void onShowGeneratePIN();
        void onshowAccountStatus();
        void onShowMyVirtualCardAccount();
        void onShowMyChangeNip();
        void onShowBlockCard();
    }
}
