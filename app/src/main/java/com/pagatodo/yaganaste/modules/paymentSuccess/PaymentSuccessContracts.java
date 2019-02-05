package com.pagatodo.yaganaste.modules.paymentSuccess;

import com.pagatodo.yaganaste.modules.management.apis.ListenerFriggs;

public class PaymentSuccessContracts {

    public interface Listener {
        void onError(String msj);
        void succesNotification();
    }

    public interface Interactor extends ListenerFriggs{
        void paymentNotification(String plate, String amount, String concept);
    }
}
