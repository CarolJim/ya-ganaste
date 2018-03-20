package com.pagatodo.yaganaste.ui.addfavorites.interfases;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.AddFavoritesRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.AddFotoFavoritesRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DeleteFavoriteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EditFavoritesRequest;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.utils.camera.CameraManager;

/**
 * Created by Francisco Manzo on 14/09/2017.
 */

public interface IFavoritesPresenter {

    void toPresenterAddFavorites(String textLoader, AddFavoritesRequest addFavoritesRequest);

    void toPresenterErrorServer(String mMensaje);

    void toPresenterGenericSuccess(DataSourceResult dataSourceResult);

    void toPresenterGenericError(DataSourceResult dataSourceResult);

    void openMenuPhoto(int i, CameraManager cameraManager);

    void toPresenterAddNewFavorites(String textLoader, AddFavoritesRequest addFavoritesRequest);

    void toPresenterAddFotoFavorites(AddFotoFavoritesRequest addFotoFavoritesRequest, int idFavorito);

    void toPresenterEditNewFavorites(EditFavoritesRequest editFavoritesRequest, int idFavorito);

    void toPresenterDeleteFavorite(DeleteFavoriteRequest deleteFavoriteRequest, int idFavorito);

    void updateLocalFavorite(Favoritos favoritos);

    boolean alreadyExistFavorite(String reference, int idComercio);

    void getTitularName(String trim);

    void onError(WebService consultarTitularCuenta, String string);
}
