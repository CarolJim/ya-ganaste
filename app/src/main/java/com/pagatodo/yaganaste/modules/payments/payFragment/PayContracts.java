package com.pagatodo.yaganaste.modules.payments.payFragment;

import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.modules.management.apis.ListenerLauncher;

import java.util.List;

public class PayContracts {

    public interface Listener extends ListenerLauncher {
        void onCommerceSuccess(List<Comercio> catalogos);
        void onFavoritesSuccess(List<Favoritos> catalogos, TYPE_PAY typePay);
    }

    public interface Interactor{
        void getComerces();
        //void getFavorites();
        void getFavoritesLocal();
    }

    public enum TYPE_PAY{
        RECHARGE,SERVICES
    }

}
