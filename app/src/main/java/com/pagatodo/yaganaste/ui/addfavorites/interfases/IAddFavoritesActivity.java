package com.pagatodo.yaganaste.ui.addfavorites.interfases;

/**
 * Created by Francisco Manzo on 14/09/2017.
 */

public interface IAddFavoritesActivity {

    void toViewErrorServer(String mMensaje);

    void toViewSuccessAdd(String mensaje);

    void showLoader(String s);

    void hideLoader();

    void showExceptionToView(String s);
}
