package com.pagatodo.yaganaste.modules.qr;

public class QrManagerContracts {

    public interface Listener{

    }

    public interface Iteractor{
        void getMyQrs();
    }

    public interface Router{
        void showOperation(int idFragment);
    }
}
