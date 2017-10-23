package com.pagatodo.yaganaste.ui.addfavorites.presenters;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.AddFavoritesRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.AddFotoFavoritesRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.DeleteFavoriteRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EditFavoritesRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosDatosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosDeleteDatosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosEditDatosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosNewDatosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosNewFotoDatosResponse;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IAddFavoritesActivity;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IFavoritesIteractor;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IFavoritesPresenter;
import com.pagatodo.yaganaste.ui.addfavorites.iteractors.FavoritesIteractor;
import com.pagatodo.yaganaste.utils.camera.CameraManager;

/**
 * Created by Francisco Manzo on 14/09/2017.
 */

public class FavoritesPresenter implements IFavoritesPresenter {
    IAddFavoritesActivity mView;
    IFavoritesIteractor favoritesIteractor;
    private CatalogsDbApi api;
    int idFavorito;

    public FavoritesPresenter(IAddFavoritesActivity mView) {
        this.mView = mView;
        favoritesIteractor = new FavoritesIteractor(this);
        this.api = new CatalogsDbApi(App.getContext());
    }

    @Override
    public void toPresenterAddFavorites(AddFavoritesRequest addFavoritesRequest) {
        mView.showLoader("Procesando Datos");
        favoritesIteractor.toIteractorAddFavorites(addFavoritesRequest);
    }


    @Override
    public void toPresenterAddNewFavorites(String textLoader, AddFavoritesRequest addFavoritesRequest) {
        //mView.showLoader("Procesando Datos");
        mView.showLoader(textLoader);
        favoritesIteractor.toIteractorAddNewFavorites(addFavoritesRequest);
    }

    @Override
    public void toPresenterAddFotoFavorites(AddFotoFavoritesRequest addFotoFavoritesRequest, int idFavorito) {
        mView.showLoader(App.getContext().getString(R.string.fav_photo_request));
        favoritesIteractor.toIteractorAddFotoNewFavorites(addFotoFavoritesRequest, idFavorito);
    }

    @Override
    public void toPresenterEditNewFavorites(EditFavoritesRequest editFavoritesRequest, int idFavorito) {
        mView.showLoader(App.getContext().getString(R.string.update_fav_request));
        favoritesIteractor.toIteractorEditFavorites(editFavoritesRequest, idFavorito);
    }

    @Override
    public void toPresenterDeleteFavorite(DeleteFavoriteRequest deleteFavoriteRequest, int idFavorito) {
        mView.showLoader("Procesando Peticion");
        this.idFavorito = idFavorito;
        favoritesIteractor.toIteractorDeleteFavorite(deleteFavoriteRequest, idFavorito);
    }

    @Override
    public void updateLocalFavorite(DataFavoritos dataFavoritos) {
        api.deleteFavorite((int) dataFavoritos.getIdFavorito());
        api.insertFavorite(dataFavoritos);
        mView.hideLoader();
    }

    @Override
    public boolean alreadyExistFavorite(String reference, int idComercio) {
        return api.favoriteExists(reference, idComercio);
    }

    /**
     * RESPUESTAS DE ITERACTOR
     */

    /**
     * @param dataSourceResult
     */
    @Override
    public void toPresenterGenericSuccess(DataSourceResult dataSourceResult) {

        /*
         * Instancia de peticion exitosa y operacion exitosa de CambiarEmailResponse
         */
        if (dataSourceResult.getData() instanceof FavoritosDatosResponse) {
            // Damos de alta el Dato en la DB
            DataFavoritos dataFavoritos = new DataFavoritos(((FavoritosDatosResponse) dataSourceResult.getData()).getData().getColorMarca(),
                    ((FavoritosDatosResponse) dataSourceResult.getData()).getData().getIdComercio(),
                    ((FavoritosDatosResponse) dataSourceResult.getData()).getData().getIdCuenta(),
                    ((FavoritosDatosResponse) dataSourceResult.getData()).getData().getIdFavorito(),
                    ((FavoritosDatosResponse) dataSourceResult.getData()).getData().getIdTipoComercio(),
                    ((FavoritosDatosResponse) dataSourceResult.getData()).getData().getImagenURL(),
                    ((FavoritosDatosResponse) dataSourceResult.getData()).getData().getImagenURLComercio(),
                    ((FavoritosDatosResponse) dataSourceResult.getData()).getData().getImagenURLComercioColor(),
                    ((FavoritosDatosResponse) dataSourceResult.getData()).getData().getNombre(),
                    ((FavoritosDatosResponse) dataSourceResult.getData()).getData().getNombreComercio(),
                    ((FavoritosDatosResponse) dataSourceResult.getData()).getData().getReferencia());

            api.insertFavorite(dataFavoritos);
            mView.hideLoader();
            FavoritosDatosResponse response = (FavoritosDatosResponse) dataSourceResult.getData();
            mView.toViewSuccessAdd(response);
        }

        /**
         * Instancia de peticion exitosa y operacion exitosa de FavoritosNewDatosResponse
         */
        if (dataSourceResult.getData() instanceof FavoritosNewDatosResponse) {
            // Damos de alta el Dato en la DB
            DataFavoritos dataFavoritos = new DataFavoritos(
                    ((FavoritosNewDatosResponse) dataSourceResult.getData()).getData().getColorMarca(),
                    ((FavoritosNewDatosResponse) dataSourceResult.getData()).getData().getIdComercio(),
                    ((FavoritosNewDatosResponse) dataSourceResult.getData()).getData().getIdCuenta(),
                    ((FavoritosNewDatosResponse) dataSourceResult.getData()).getData().getIdFavorito(),
                    ((FavoritosNewDatosResponse) dataSourceResult.getData()).getData().getIdTipoComercio(),
                    ((FavoritosNewDatosResponse) dataSourceResult.getData()).getData().getImagenURL(),
                    ((FavoritosNewDatosResponse) dataSourceResult.getData()).getData().getImagenURLComercio(),
                    ((FavoritosNewDatosResponse) dataSourceResult.getData()).getData().getImagenURLComercioColor(),
                    ((FavoritosNewDatosResponse) dataSourceResult.getData()).getData().getNombre(),
                    ((FavoritosNewDatosResponse) dataSourceResult.getData()).getData().getNombreComercio(),
                    ((FavoritosNewDatosResponse) dataSourceResult.getData()).getData().getReferencia());

            api.insertFavorite(dataFavoritos);
            mView.hideLoader();
            FavoritosNewDatosResponse response = (FavoritosNewDatosResponse) dataSourceResult.getData();
            mView.toViewSuccessAdd(response);
        }

        /**
         * Instancia de peticion exitosa y operacion exitosa de FavoritosNewDatosResponse
         */
        if (dataSourceResult.getData() instanceof FavoritosNewFotoDatosResponse) {
            // mView.hideLoader();

            // Eliminamos el Favorito con el ID que obtenemos
            int idFavoritoOld = ((FavoritosNewFotoDatosResponse) dataSourceResult.getData()).getData().getIdFavorito();
            api.deleteFavorite(idFavoritoOld);

            DataFavoritos dataFavoritos = new DataFavoritos(
                    ((FavoritosNewFotoDatosResponse) dataSourceResult.getData()).getData().getColorMarca(),
                    ((FavoritosNewFotoDatosResponse) dataSourceResult.getData()).getData().getIdComercio(),
                    ((FavoritosNewFotoDatosResponse) dataSourceResult.getData()).getData().getIdCuenta(),
                    ((FavoritosNewFotoDatosResponse) dataSourceResult.getData()).getData().getIdFavorito(),
                    ((FavoritosNewFotoDatosResponse) dataSourceResult.getData()).getData().getIdTipoComercio(),
                    ((FavoritosNewFotoDatosResponse) dataSourceResult.getData()).getData().getImagenURL(),
                    ((FavoritosNewFotoDatosResponse) dataSourceResult.getData()).getData().getImagenURLComercio(),
                    ((FavoritosNewFotoDatosResponse) dataSourceResult.getData()).getData().getImagenURLComercioColor(),
                    ((FavoritosNewFotoDatosResponse) dataSourceResult.getData()).getData().getNombre(),
                    ((FavoritosNewFotoDatosResponse) dataSourceResult.getData()).getData().getNombreComercio(),
                    ((FavoritosNewFotoDatosResponse) dataSourceResult.getData()).getData().getReferencia());

            api.insertFavorite(dataFavoritos);

            mView.hideLoader();
            FavoritosNewFotoDatosResponse response = (FavoritosNewFotoDatosResponse) dataSourceResult.getData();
            mView.toViewSuccessAddFoto(response.getMensaje());
        }


        /**
         * Instancia de peticion exitosa y operacion exitosa de FavoritosNewDatosResponse
         */
        if (dataSourceResult.getData() instanceof FavoritosEditDatosResponse) {
            //mView.hideLoader();
            FavoritosEditDatosResponse response = (FavoritosEditDatosResponse) dataSourceResult.getData();
            mView.toViewSuccessEdit(response);
        }


        /**
         * Instancia de peticion exitosa y operacion exitosa de FavoritosNewDatosResponse
         */
        if (dataSourceResult.getData() instanceof FavoritosDeleteDatosResponse) {
            mView.hideLoader();
            api.deleteFavorite(idFavorito);
            idFavorito = 0;
            FavoritosDeleteDatosResponse response = (FavoritosDeleteDatosResponse) dataSourceResult.getData();
            mView.toViewSuccessDeleteFavorite(response.getMensaje());
        }
    }

    @Override
    public void toPresenterGenericError(DataSourceResult dataSourceResult) {

        /**
         * Instancia de peticion exitosa y operacion exitosa de BloquearCuentaResponse
         */
        if (dataSourceResult.getData() instanceof FavoritosDatosResponse) {
            mView.hideLoader();
            FavoritosDatosResponse response = (FavoritosDatosResponse) dataSourceResult.getData();
            mView.toViewErrorServer(response.getMensaje());
        }

        /**
         * Instancia de peticion exitosa y operacion exitosa de FavoritosNewDatosResponse
         */
        if (dataSourceResult.getData() instanceof FavoritosNewDatosResponse) {
            //mView.hideLoader();
            mView.hideLoader();
            FavoritosNewDatosResponse response = (FavoritosNewDatosResponse) dataSourceResult.getData();
            mView.toViewErrorServer(response.getMensaje());
        }

        /**
         * Instancia de peticion exitosa y operacion exitosa de FavoritosNewFotoDatosResponse
         */
        if (dataSourceResult.getData() instanceof FavoritosNewFotoDatosResponse) {
            //mView.hideLoader();
            mView.hideLoader();
            FavoritosNewFotoDatosResponse response = (FavoritosNewFotoDatosResponse) dataSourceResult.getData();
            mView.toViewErrorServer(response.getMensaje());
        }

        /**
         * Instancia de peticion exitosa y operacion exitosa de FavoritosEditDatosResponse
         */
        if (dataSourceResult.getData() instanceof FavoritosEditDatosResponse) {
            //mView.hideLoader();
            mView.hideLoader();
            FavoritosEditDatosResponse response = (FavoritosEditDatosResponse) dataSourceResult.getData();
            mView.toViewErrorServer(response.getMensaje());
        }

        /**
         * Instancia de peticion exitosa y operacion exitosa de FavoritosDeleteDatosResponse
         */
        if (dataSourceResult.getData() instanceof FavoritosDeleteDatosResponse) {
            //mView.hideLoader();
            mView.hideLoader();
            FavoritosDeleteDatosResponse response = (FavoritosDeleteDatosResponse) dataSourceResult.getData();
            mView.toViewErrorServer(response.getMensaje());
        }

    }

    @Override
    public void openMenuPhoto(int i, CameraManager cameraManager) {
        try {
            cameraManager.createPhoto(1);
        } catch (Exception e) {
            //Toast.makeText(App.getContext(), "Exception " + e, Toast.LENGTH_SHORT).show();
            mView.showExceptionToView(e.toString());
        }
    }

    @Override
    public void toPresenterErrorServer(String mMensaje) {
        mView.hideLoader();
        mView.toViewErrorServer(mMensaje);
    }
}
