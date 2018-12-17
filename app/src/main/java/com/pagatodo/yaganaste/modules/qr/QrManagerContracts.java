package com.pagatodo.yaganaste.modules.qr;

import com.pagatodo.yaganaste.modules.data.QrItems;

import java.util.ArrayList;

public class QrManagerContracts {

    public interface Listener{
        void onSuccessQRs(ArrayList<QrItems> listQRs);
    }

    public interface Iteractor{
        void getMyQrs();
    }

    public interface Router{
        void showOperation(int idFragment);
    }
}
