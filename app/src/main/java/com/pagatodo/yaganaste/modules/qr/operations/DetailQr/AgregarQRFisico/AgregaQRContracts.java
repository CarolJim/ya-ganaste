package com.pagatodo.yaganaste.modules.qr.operations.DetailQr.AgregarQRFisico;

import com.pagatodo.yaganaste.modules.data.QrItems;

import java.util.ArrayList;

public class AgregaQRContracts {


    public interface Listener{
        void onSuccessQRs();
        void onErrorQRs();
    }

    public interface Iteractor{
        void asociaQRValido(String Plate ,String alias);
    }
}
