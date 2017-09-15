package com.pagatodo.yaganaste.ui.addfavorites.iteractors;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.AddFavoritesRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.AddFavoritosResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.ISessionExpired;
import com.pagatodo.yaganaste.net.ApiTrans;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IFavoritesIteractor;
import com.pagatodo.yaganaste.ui.addfavorites.interfases.IFavoritesPresenter;
import com.pagatodo.yaganaste.utils.Recursos;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ADD_FAVORITES;

/**
 * Created by Francisco Manzo on 14/09/2017.
 */

public class FavoritesIteractor implements IFavoritesIteractor, IRequestResult {
    IFavoritesPresenter favoritesPresenter;

    public FavoritesIteractor(IFavoritesPresenter favoritesPresenter) {
        this.favoritesPresenter = favoritesPresenter;
    }

    @Override
    public void toIteractorAddFavorites(AddFavoritesRequest addFavoritesRequest) {
        try {
            ApiTrans.addFavorites(addFavoritesRequest, this);
        } catch (OfflineException e) {
            // e.printStackTrace();
            //favoritesPresenter.showExceptionAvatarToPresenter(e.toString());
        }
        //favoritesPresenter.toPresenterTestResult();
    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        /**
         * Instancia de peticion exitosa de AddFavorites
         */
        if (dataSourceResult.getData() instanceof AddFavoritosResponse) {
            AddFavoritosResponse response = (AddFavoritosResponse) dataSourceResult.getData();

            if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
                //Log.d("PreferUserIteractor", "EstatusCuentaResponse Sucess " + response.getMensaje());
                favoritesPresenter.toPresenterGenericSuccess(dataSourceResult);
            } else {
                //Log.d("PreferUserIteractor", "EstatusCuentaResponse Sucess with Error " + response.getMensaje());
                favoritesPresenter.toPresenterGenericError(dataSourceResult);
            }
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        if (error.getWebService().equals(ADD_FAVORITES)) {
            favoritesPresenter.toPresenterErrorServer(error.getData().toString());
        }
    }
}
