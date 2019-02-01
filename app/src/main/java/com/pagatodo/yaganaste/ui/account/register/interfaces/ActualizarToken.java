package com.pagatodo.yaganaste.ui.account.register.interfaces;

public class ActualizarToken {

    public interface Listener{
        void onSuccessToken();
        void onErrorToken();
    }

    public interface Iteractor{
        void updateTokenFirebase();
    }



}




