package com.pagatodo.yaganaste.modules.PaymentImp;

import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.modules.management.apis.ListenerLauncher;

import java.util.List;

public class PayContracts {

    public interface Listener extends ListenerLauncher {
        void onRechargeCommerceSucces(List<Comercio> catalogos);
    }

    public interface Interactor{
        void getRechargeCommerce();
    }
}
