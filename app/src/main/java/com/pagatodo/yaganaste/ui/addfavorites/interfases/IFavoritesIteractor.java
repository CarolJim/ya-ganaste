package com.pagatodo.yaganaste.ui.addfavorites.interfases;

import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.AddFavoritesRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.AddFotoFavoritesRequest;

/**
 * Created by Francisco Manzo on 14/09/2017.
 */

public interface IFavoritesIteractor {
    void toIteractorAddFavorites(AddFavoritesRequest addFavoritesRequest);

    void toIteractorAddNewFavorites(AddFavoritesRequest addFavoritesRequest);

    void toIteractorAddFotoNewFavorites(AddFotoFavoritesRequest addFotoFavoritesRequest, int idFavorito);
}
