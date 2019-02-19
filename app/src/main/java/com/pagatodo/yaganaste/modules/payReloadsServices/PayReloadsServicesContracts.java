package com.pagatodo.yaganaste.modules.payReloadsServices;

import com.pagatodo.view_manager.controllers.dataholders.IconButtonDataHolder;
import com.pagatodo.view_manager.recyclers.RechargesRecycler;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.modules.management.apis.ListenerLauncher;

import java.util.ArrayList;
import java.util.List;

public class PayReloadsServicesContracts {

    public interface Listener extends ListenerLauncher {
        void onRechargeCommerceSucces(List<Comercio> catalogos);
    }

    interface Interactor{
        void getRechargeFavorites();
    }

    interface Router{
        void onShowAllRecharges(ArrayList<IconButtonDataHolder> list);
        void onShowAllFavRefills();
        void onShowAddFavoriteRecharge();
    }
}
