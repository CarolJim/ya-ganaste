package com.pagatodo.yaganaste.modules.payments.payFragment;

import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.pagatodo.yaganaste.modules.payments.payFragment.PayContracts.TYPE_PAY.RECHARGE;
import static com.pagatodo.yaganaste.modules.payments.payFragment.PayContracts.TYPE_PAY.SERVICES;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_RECARGAS;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_SERVICIOS;

public class PayInteractor implements PayContracts.Interactor{

    private PayContracts.Listener listener;
    private PayContracts.TYPE_PAY typePay;

    PayInteractor(PayContracts.Listener listener, PayContracts.TYPE_PAY typePay) {
        this.listener = listener;
        this.typePay = typePay;
    }

    @Override
    public void getComerces() {
        List<Comercio> catalogos = new ArrayList<>();
        try {
            switch (this.typePay){
                case RECHARGE:
                    catalogos = new DatabaseManager().getComerciosByType(PAYMENT_RECARGAS);
                    break;
                case SERVICES:
                    catalogos = new DatabaseManager().getComerciosByType(PAYMENT_SERVICIOS);
                    break;
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (catalogos.size() > 0) {
            this.listener.onCommerceSuccess(catalogos);
        } else {
            this.listener.onError("No se encontrarón comercios");
        }

    }
    @Override
    public void getFavoritesLocal() {

        try {
            switch (this.typePay){
                case RECHARGE:
                    this.listener.onFavoritesSuccess(new DatabaseManager()
                            .getListFavoritosByIdComercio(PAYMENT_RECARGAS),RECHARGE);
                    break;
                case SERVICES:
                    this.listener.onFavoritesSuccess(new DatabaseManager()
                            .getListFavoritosByIdComercio(PAYMENT_SERVICIOS),SERVICES);
                    break;
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
