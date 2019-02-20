package com.pagatodo.yaganaste.modules.payments.payReloadsServices.allRecharges;

import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_RECARGAS;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_SERVICIOS;

public class AllRechargesInteractor implements AllRechargesContracts.Interactor{

    private AllRechargesContracts.Listener listener;

    AllRechargesInteractor(AllRechargesContracts.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void getRecharge() {
        List<Comercio> catalogos = new ArrayList<>();
        try {
            catalogos = new DatabaseManager().getComerciosByType(PAYMENT_RECARGAS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (catalogos.size() > 0) {
            this.listener.onSuccsessRechargeAndServices(catalogos);
        } else {
            this.listener.onError("No se encontrarón comercios");
        }
    }

    @Override
    public void getServices() {
        List<Comercio> catalogos = new ArrayList<>();
        try {
            catalogos = new DatabaseManager().getComerciosByType(PAYMENT_SERVICIOS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (catalogos.size() > 0) {
            this.listener.onSuccsessRechargeAndServices(catalogos);
        } else {
            this.listener.onError("No se encontrarón comercios");
        }
    }

    @Override
    public void getRechargeFavorites() {
        try {
            this.listener.onFavoritesSuccess(new DatabaseManager()
                    .getListFavoritosByIdComercio(PAYMENT_RECARGAS));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getServicesFavorites() {
        try {
            this.listener.onFavoritesSuccess(new DatabaseManager()
                    .getListFavoritosByIdComercio(PAYMENT_SERVICIOS));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
