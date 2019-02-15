package com.pagatodo.yaganaste.modules.PaymentImp;

import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_RECARGAS;

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
}
