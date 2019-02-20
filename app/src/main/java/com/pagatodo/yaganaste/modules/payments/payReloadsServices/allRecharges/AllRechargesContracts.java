package com.pagatodo.yaganaste.modules.payments.payReloadsServices.allRecharges;

import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.modules.management.apis.ListenerLauncher;

import java.util.List;

class AllRechargesContracts {

    interface Listener extends ListenerLauncher {
        void onSuccsessRechargeAndServices(List<Comercio> comercios);
        void onFavoritesSuccess(List<Favoritos> favoritos);
    }

    interface Interactor{
        void getRecharge();
        void getServices();
        void getRechargeFavorites();
        void getServicesFavorites();
    }
}
