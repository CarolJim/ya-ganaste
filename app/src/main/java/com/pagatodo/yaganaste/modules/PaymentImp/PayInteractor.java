package com.pagatodo.yaganaste.modules.PaymentImp;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ConsultarFavoritosResponse;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_RECARGAS;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_SERVICIOS;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

public class PayInteractor implements PayContracts.Interactor{

    private PayContracts.Listener listener;

    PayInteractor(PayContracts.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void getRechargeCommerce() {
        List<Comercio> catalogos = new ArrayList<>();
        try {
            catalogos = new DatabaseManager().getComerciosByType(PAYMENT_RECARGAS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (catalogos.size() > 0) {
            this.listener.onRechargeCommerceSucces(catalogos);
        } else {
            this.listener.onError("No se encontrarón comercios");
        }
    }

    @Override
    public void getPayServices() {
        List<Comercio> catalogos = new ArrayList<>();
        try {
            catalogos = new DatabaseManager().getComerciosByType(PAYMENT_SERVICIOS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (catalogos.size() > 0) {
            this.listener.onPayServicesSuccess(catalogos);
        } else {
            this.listener.onError("No se encontrarón comercios");
        }
    }

    public void getRechargeFavorites(){
        try {
            ApiAdtvo.consultarFavoritos(this);
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(DataSourceResult result) {
        switch (result.getWebService()) {
            case OBTENER_FAVORITOS:
                //mPresenter.onSuccessWSFavorites(dataSourceResult, typeDataFav);
                ConsultarFavoritosResponse response = (ConsultarFavoritosResponse) result.getData();
                if (response.getCodigoRespuesta() == CODE_OK) {
                    try {
                        if (response.getData().size() > 0) {
                            new DatabaseManager().insertListFavorites(response.getData());
                        }
                        this.getRechargeFavLocal();
                        //mInteractor.getFavoritesFromDB(typeDataFav);
                    } catch (Exception e) {
                        e.printStackTrace();
                        //   mView.showError();
                        this.listener.onError("No se encontraron favoritos");
                    }
                } else {
                    // mView.showError();
                    this.listener.onError(response.getMensaje());
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onFailed(DataSourceResult error) {

    }

    @Override
    public void getRechargeFavLocal() {
        List<Favoritos> catalogos = new ArrayList<>();
        try {
            catalogos = new DatabaseManager().getListFavoritosByIdComercio(PAYMENT_RECARGAS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.listener.onRechargeFavorites(catalogos);
    }

    @Override
    public void getPayServicesFavLocal() {
        List<Favoritos> catalogos = new ArrayList<>();
        try {
            catalogos = new DatabaseManager().getListFavoritosByIdComercio(PAYMENT_SERVICIOS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.listener.onRechargeFavorites(catalogos);
    }
}
