package com.pagatodo.yaganaste.ui.addfavorites.presenters;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.AddFavoritesRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.AddFavoritosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.FavoritosDatosResponse;
import com.pagatodo.yaganaste.ui._controllers.manager.AddFavoritesActivity;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IAddFavoritesActivity;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IFavoritesIteractor;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IFavoritesPresenter;
import com.pagatodo.yaganaste.ui.addfavorites.iteractors.FavoritesIteractor;
import com.pagatodo.yaganaste.utils.camera.CameraManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Francisco Manzo on 14/09/2017.
 */

public class FavoritesPresenter implements IFavoritesPresenter {
    AddFavoritesActivity mView;
    IFavoritesIteractor favoritesIteractor;
    private CatalogsDbApi api;

    public FavoritesPresenter(AddFavoritesActivity mView) {
        this.mView = mView;
        favoritesIteractor = new FavoritesIteractor(this);
        this.api = new CatalogsDbApi(mView);
    }

    @Override
    public void toPresenterAddFavorites(AddFavoritesRequest addFavoritesRequest) {
        mView.showLoader("Procesando Datos");
        favoritesIteractor.toIteractorAddFavorites(addFavoritesRequest);
    }

    @Override
    public void toPresenterGenericSuccess(DataSourceResult dataSourceResult) {

        /**
         * Instancia de peticion exitosa y operacion exitosa de CambiarEmailResponse
         */
        if (dataSourceResult.getData() instanceof FavoritosDatosResponse) {
            // Damos de alta el Dato en la DB
            List<DataFavoritos> dataFavoritos = new ArrayList<>();
            dataFavoritos.add(new DataFavoritos(
                    ((FavoritosDatosResponse) dataSourceResult.getData()).getData().getColorMarca(),
                    ((FavoritosDatosResponse) dataSourceResult.getData()).getData().getIdComercio(),
                    ((FavoritosDatosResponse) dataSourceResult.getData()).getData().getIdCuenta(),
                    ((FavoritosDatosResponse) dataSourceResult.getData()).getData().getIdFavorito(),
                    ((FavoritosDatosResponse) dataSourceResult.getData()).getData().getIdTipoComercio(),
                    ((FavoritosDatosResponse) dataSourceResult.getData()).getData().getImagenURL(),
                    ((FavoritosDatosResponse) dataSourceResult.getData()).getData().getImagenURLComercio(),
                    ((FavoritosDatosResponse) dataSourceResult.getData()).getData().getImagenURLComercioColor(),
                    ((FavoritosDatosResponse) dataSourceResult.getData()).getData().getNombre(),
                    ((FavoritosDatosResponse) dataSourceResult.getData()).getData().getNombreComercio(),
                    ((FavoritosDatosResponse) dataSourceResult.getData()).getData().getReferencia()
            ));

            api.insertFavorites(dataFavoritos);

            mView.hideLoader();
            FavoritosDatosResponse response = (FavoritosDatosResponse) dataSourceResult.getData();
            mView.toViewSuccessAdd(response.getMensaje());
        }

    }

    @Override
    public void toPresenterGenericError(DataSourceResult dataSourceResult) {

        /**
         * Instancia de peticion exitosa y operacion exitosa de BloquearCuentaResponse
         */
        if (dataSourceResult.getData() instanceof FavoritosDatosResponse) {
            //mView.hideLoader();
            mView.hideLoader();
            FavoritosDatosResponse response = (FavoritosDatosResponse) dataSourceResult.getData();
            mView.toViewErrorServer(response.getMensaje());
        }

    }

    @Override
    public void openMenuPhoto(int i, CameraManager cameraManager) {
        try {
            cameraManager.createPhoto(1);
        } catch (Exception e) {
            //Toast.makeText(App.getContext(), "Exception " + e, Toast.LENGTH_SHORT).show();
           // mView.showExceptionToView(e.toString());
        }
    }

    @Override
    public void toPresenterErrorServer(String mMensaje) {
        mView.hideLoader();
        mView.toViewErrorServer(mMensaje);
    }
}
