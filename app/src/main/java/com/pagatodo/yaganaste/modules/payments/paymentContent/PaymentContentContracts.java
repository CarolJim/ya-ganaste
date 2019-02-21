package com.pagatodo.yaganaste.modules.payments.paymentContent;

import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.modules.management.apis.ListenerLauncher;


public class PaymentContentContracts {

    public interface Listener extends ListenerLauncher {
        void onFavoritesSuccess();
    }

    public interface Interactor extends IRequestResult {
        void getFavorites();
    }
}
