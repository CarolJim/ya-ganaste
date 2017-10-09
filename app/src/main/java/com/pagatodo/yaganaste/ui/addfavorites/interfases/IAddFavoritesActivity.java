package com.pagatodo.yaganaste.ui.addfavorites.interfases;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosDatosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosEditDatosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosNewDatosResponse;

/**
 * Created by Francisco Manzo on 14/09/2017.
 */

public interface IAddFavoritesActivity {

    void toViewErrorServer(String mMensaje);

    void toViewSuccessAdd(FavoritosNewDatosResponse mensaje);

    void showLoader(String s);

    void hideLoader();

    void showExceptionToView(String s);

    void toViewSuccessAddFoto(String mensaje);

    void toViewSuccessAdd(FavoritosDatosResponse response);

    void toViewSuccessDeleteFavorite(String mensaje);

    void toViewSuccessEdit(FavoritosEditDatosResponse response);


}
