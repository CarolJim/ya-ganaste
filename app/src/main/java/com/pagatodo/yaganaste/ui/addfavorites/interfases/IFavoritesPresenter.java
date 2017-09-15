package com.pagatodo.yaganaste.ui.addfavorites.interfases;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.AddFavoritesRequest;
import com.pagatodo.yaganaste.utils.camera.CameraManager;

/**
 * Created by Francisco Manzo on 14/09/2017.
 */

public interface IFavoritesPresenter {
    void toPresenterAddFavorites(AddFavoritesRequest addFavoritesRequest);

    void toPresenterTestResult();

    void toPresenterErrorServer(String mMensaje);

    void toPresenterGenericSuccess(DataSourceResult dataSourceResult);

    void toPresenterGenericError(DataSourceResult dataSourceResult);

    void openMenuPhoto(int i, CameraManager cameraManager);
}
