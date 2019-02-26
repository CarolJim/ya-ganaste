package com.pagatodo.yaganaste.modules.payments.payReloadsServices;

import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.modules.management.apis.ListenerLauncher;

import java.util.List;

public class PayReloadsServicesContracts {

    public interface Listener extends ListenerLauncher {
        void onRechargeCommerceSucces(List<Comercio> catalogos);
    }

    interface Interactor{
        void getRechargeFavorites();
    }

    interface Router{
        void onShowAllRecharges(PayReloadsServicesActivity.Type type);
        void onShowAllFavRefills();
        void onShowAddFavoriteRecharge();
    }
}
