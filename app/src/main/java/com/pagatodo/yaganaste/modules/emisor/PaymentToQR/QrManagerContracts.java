package com.pagatodo.yaganaste.modules.emisor.PaymentToQR;

import com.pagatodo.yaganaste.modules.data.QrItems;

import java.util.ArrayList;

public class QrManagerContracts {

    public interface Listener{
        void onSuccessQRs(ArrayList<QrItems> listQRs);
        void onErrorQRs();
        void onSuccesDel();
    }

    public interface Iteractor{
        void getMyQrs();
        void delQR(QrItems item);
    }

    public interface Router{
        void showOperation(int idFragment);
        void showOperationDetail(QrItems item);
    }
}
