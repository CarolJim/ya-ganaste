package com.pagatodo.yaganaste.ui.addfavorites.presenters;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.AddFavoritesRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.AddFavoritosResponse;
import com.pagatodo.yaganaste.ui._controllers.manager.AddFavoritesActivity;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IAddFavoritesActivity;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IFavoritesIteractor;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IFavoritesPresenter;
import com.pagatodo.yaganaste.ui.addfavorites.iteractors.FavoritesIteractor;
import com.pagatodo.yaganaste.utils.camera.CameraManager;

/**
 * Created by Francisco Manzo on 14/09/2017.
 */

public class FavoritesPresenter implements IFavoritesPresenter {
    AddFavoritesActivity mView;
    IFavoritesIteractor favoritesIteractor;

    public FavoritesPresenter(AddFavoritesActivity mView) {
        this.mView = mView;
        favoritesIteractor = new FavoritesIteractor(this);
    }

    @Override
    public void toPresenterAddFavorites(AddFavoritesRequest addFavoritesRequest) {
        mView.showLoader("Procesando Datos");
        favoritesIteractor.toIteractorAddFavorites(addFavoritesRequest);
    }

    @Override
    public void toPresenterTestResult() {
        mView.toViewResult();
    }

    @Override
    public void toPresenterGenericSuccess(DataSourceResult dataSourceResult) {

        /**
         * Instancia de peticion exitosa y operacion exitosa de CambiarEmailResponse
         */
        if (dataSourceResult.getData() instanceof AddFavoritosResponse) {
            mView.hideLoader();
            AddFavoritosResponse response = (AddFavoritosResponse) dataSourceResult.getData();
            mView.toViewSuccessAdd(response.getMensaje());
        }

    }

    @Override
    public void toPresenterGenericError(DataSourceResult dataSourceResult) {

        /**
         * Instancia de peticion exitosa y operacion exitosa de BloquearCuentaResponse
         */
        if (dataSourceResult.getData() instanceof AddFavoritosResponse) {
            //mView.hideLoader();
            mView.hideLoader();
            AddFavoritosResponse response = (AddFavoritosResponse) dataSourceResult.getData();
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
