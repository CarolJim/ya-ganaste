package com.pagatodo.yaganaste.modules.qr;

import com.pagatodo.yaganaste.modules.data.QrItem;
import com.pagatodo.yaganaste.modules.data.QrItems;

import org.json.JSONException;

import java.util.ArrayList;

public class QrManagerContracts {

    public interface Listener{
        void onSuccessQRs(ArrayList<QrItems> listQRs);
    }

    public interface Iteractor{
        void getMyQrs() throws JSONException;
    }

    public interface Router{
        void showOperation(int idFragment);
        void showOperationDetail(QrItem item);
    }
}
