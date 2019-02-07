package com.pagatodo.yaganaste.modules.emisor.GeneratePIN;

class GeneratePINContracts {
    interface Listener{
        void onShowLoading();
        void onHideLoading();
        void onSucces();
        void onFail(String error);
    }
    interface Interactor{
        void onAsignNIP();
        void onBlockCard(String numberCard);
        void onRemplaceCard(String currentCard, String newCard);
    }
}
