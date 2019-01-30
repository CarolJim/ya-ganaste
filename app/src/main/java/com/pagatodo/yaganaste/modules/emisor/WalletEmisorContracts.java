package com.pagatodo.yaganaste.modules.emisor;

import com.pagatodo.yaganaste.interfaces.IRequestResult;

public class WalletEmisorContracts {

    public interface Listener{
        void showLoad();
        void hideLoad();
        void onSouccesValidateCard();
        void onErrorRequest(String msj);
    }

    interface Interactor extends IRequestResult {
        void TemporaryBlock();
        void validateCard(String cardNumber);
    }

    interface Router{
        void onShowActivatePhysicalCard();
        void onShowGeneratePIN();
        void onshowAccountStatus();
        void onShowMyVirtualCardAccount();
        void onShowMyChangeNip();
        void onShowBlockCard();
        void onShowTemporaryBlock();
        void onShowCardActive();
    }
}
