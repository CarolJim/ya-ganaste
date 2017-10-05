package com.pagatodo.yaganaste.ui.addfavorites.iteractors;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.AddFavoritesRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.AddFotoFavoritesRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DeleteFavoriteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EditFavoritesRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosDeleteDatosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosDatosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosEditDatosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosNewDatosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosNewFotoDatosResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IFavoritesIteractor;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IFavoritesPresenter;
import com.pagatodo.yaganaste.utils.Recursos;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ADD_FAVORITES;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ADD_NEW_FAVORITES;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.ADD_NEW_FOTO_FAVORITES;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.DELETE_FAVORITE;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.EDIT_FAVORITES;

/**
 * Created by Francisco Manzo on 14/09/2017.
 */

public class FavoritesIteractor implements IFavoritesIteractor, IRequestResult {
    IFavoritesPresenter favoritesPresenter;

    public FavoritesIteractor(IFavoritesPresenter favoritesPresenter) {
        this.favoritesPresenter = favoritesPresenter;
    }

    /**
     * Enviamos el Request al ApiAdtvo para su proceso
     * @param addFavoritesRequest
     */
    @Override
    public void toIteractorAddFavorites(AddFavoritesRequest addFavoritesRequest) {
        try {
            ApiAdtvo.addFavorites(addFavoritesRequest, this);
        } catch (OfflineException e) {
            favoritesPresenter.toPresenterErrorServer(
                    App.getContext().getResources().getString(R.string.no_internet_access));
        }
        //favoritesPresenter.toPresenterTestResult();
    }

    @Override
    public void toIteractorAddNewFavorites(AddFavoritesRequest addFavoritesRequest) {
        try {
            ApiAdtvo.addNewFavorites(addFavoritesRequest, this);
        } catch (OfflineException e) {
            favoritesPresenter.toPresenterErrorServer(
                    App.getContext().getResources().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void toIteractorAddFotoNewFavorites(AddFotoFavoritesRequest addFotoFavoritesRequest, int idFavorito) {
        try {
            ApiAdtvo.addNewFotoFavorites(addFotoFavoritesRequest, idFavorito, this);
        } catch (OfflineException e) {
            favoritesPresenter.toPresenterErrorServer(
                    App.getContext().getResources().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void toIteractorEditFavorites(EditFavoritesRequest editFavoritesRequest, int idFavorito) {
        try {
            ApiAdtvo.addEditFavorites(editFavoritesRequest, idFavorito, this);
        } catch (OfflineException e) {
            favoritesPresenter.toPresenterErrorServer(
                    App.getContext().getResources().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void toIteractorDeleteFavorite(DeleteFavoriteRequest deleteFavoriteRequest, int idFavorito) {
        try {
            ApiAdtvo.addDeleteFavorite(deleteFavoriteRequest, idFavorito, this);
        } catch (OfflineException e) {
            favoritesPresenter.toPresenterErrorServer(
                    App.getContext().getResources().getString(R.string.no_internet_access));
        }
    }


    /**
     * RESPUESTAS DE APIS
     */

    /**
     *
     * @param dataSourceResult
     */
    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        /**
         * Instancia de peticion exitosa de AddFavorites
         */
        if (dataSourceResult.getData() instanceof FavoritosDatosResponse) {
            FavoritosDatosResponse response = (FavoritosDatosResponse) dataSourceResult.getData();

            if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
                favoritesPresenter.toPresenterGenericSuccess(dataSourceResult);
            } else {
                favoritesPresenter.toPresenterGenericError(dataSourceResult);
            }
        }

        /**
         * Instancia de peticion exitosa de AddNewFavorites
         */
        if (dataSourceResult.getData() instanceof FavoritosNewDatosResponse) {
            FavoritosNewDatosResponse response = (FavoritosNewDatosResponse) dataSourceResult.getData();

            if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
                //Log.d("PreferUserIteractor", "EstatusCuentaResponse Sucess " + response.getMensaje());
                favoritesPresenter.toPresenterGenericSuccess(dataSourceResult);
            } else {
                //Log.d("PreferUserIteractor", "EstatusCuentaResponse Sucess with Error " + response.getMensaje());
                favoritesPresenter.toPresenterGenericError(dataSourceResult);
            }
        }

        /**
         * Instancia de peticion exitosa de FavoritosNewFotoDatosResponse
         */
        if (dataSourceResult.getData() instanceof FavoritosNewFotoDatosResponse) {
            FavoritosNewFotoDatosResponse response = (FavoritosNewFotoDatosResponse) dataSourceResult.getData();

            if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
                //Log.d("PreferUserIteractor", "EstatusCuentaResponse Sucess " + response.getMensaje());
                favoritesPresenter.toPresenterGenericSuccess(dataSourceResult);
            } else {
                //Log.d("PreferUserIteractor", "EstatusCuentaResponse Sucess with Error " + response.getMensaje());
                favoritesPresenter.toPresenterGenericError(dataSourceResult);
            }
        }

        /**
         * Instancia de peticion exitosa de FavoritosEditDatosResponse
         */
        if (dataSourceResult.getData() instanceof FavoritosEditDatosResponse) {
            FavoritosEditDatosResponse response = (FavoritosEditDatosResponse) dataSourceResult.getData();

            if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
                //Log.d("PreferUserIteractor", "EstatusCuentaResponse Sucess " + response.getMensaje());
                favoritesPresenter.toPresenterGenericSuccess(dataSourceResult);
            } else {
                //Log.d("PreferUserIteractor", "EstatusCuentaResponse Sucess with Error " + response.getMensaje());
                favoritesPresenter.toPresenterGenericError(dataSourceResult);
            }
        }

        /**
         * Instancia de peticion exitosa de FavoritosDeleteDatosResponse
         */
        if (dataSourceResult.getData() instanceof FavoritosDeleteDatosResponse) {
            FavoritosDeleteDatosResponse response = (FavoritosDeleteDatosResponse) dataSourceResult.getData();

            if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
                //Log.d("PreferUserIteractor", "EstatusCuentaResponse Sucess " + response.getMensaje());
                favoritesPresenter.toPresenterGenericSuccess(dataSourceResult);
            } else {
                //Log.d("PreferUserIteractor", "EstatusCuentaResponse Sucess with Error " + response.getMensaje());
                favoritesPresenter.toPresenterGenericError(dataSourceResult);
            }
        }
    }

    /**
     * Manejo del fallo en el servidor al realizar la conexion
     * @param error
     */
    @Override
    public void onFailed(DataSourceResult error) {
        if (error.getWebService().equals(ADD_FAVORITES)) {
            favoritesPresenter.toPresenterErrorServer(error.getData().toString());
        }
        if (error.getWebService().equals(ADD_NEW_FAVORITES)) {
            favoritesPresenter.toPresenterErrorServer(error.getData().toString());
        }
        if (error.getWebService().equals(ADD_NEW_FOTO_FAVORITES)) {
            favoritesPresenter.toPresenterErrorServer(error.getData().toString());
        }
        if (error.getWebService().equals(EDIT_FAVORITES)) {
            favoritesPresenter.toPresenterErrorServer(error.getData().toString());
        }
        if (error.getWebService().equals(DELETE_FAVORITE)) {
            favoritesPresenter.toPresenterErrorServer(error.getData().toString());
        }
    }
}
