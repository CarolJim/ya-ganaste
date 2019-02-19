package com.pagatodo.yaganaste.modules.PaymentImp;

import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.modules.management.apis.ListenerLauncher;

import java.util.List;

public class PayContracts {

    public interface Listener extends ListenerLauncher {
        void onRechargeCommerceSucces(List<Comercio> catalogos);
        void onPayServicesSuccess(List<Comercio> catalogos);
        void onRechargeFavorites(List<Favoritos> catalogos);
    }

    public interface Interactor extends IRequestResult {
        void getRechargeCommerce();
        void getRechargeFavorites();
        void getRechargeFavLocal();
        void getPayServicesFavLocal();
        void getPayServices();
    }


}
