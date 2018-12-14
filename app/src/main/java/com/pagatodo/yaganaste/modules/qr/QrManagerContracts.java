package com.pagatodo.yaganaste.modules.qr;

import com.pagatodo.yaganaste.modules.data.QrItem;

public class QrManagerContracts {

    public interface Listener{

    }

    public interface Iteractor{
        void getMyQrs();
    }

    public interface Router{
        void showOperation(int idFragment);
        void showOperationDetail(QrItem item);
    }
}
