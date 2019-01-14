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
        void onActiveCard(String numberCard);
    }
}
