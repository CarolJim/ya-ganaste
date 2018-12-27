package com.pagatodo.yaganaste.modules.qr.AgregarQRFisico;

import com.pagatodo.yaganaste.modules.data.QrItems;

import java.util.ArrayList;

public class AgregaQRContractsisma {


    public interface Listener{
        void onSuccessQRs();
        void onErrorQRs();
    }

    public interface Iteractor{
        void asociaQRValido(String Plate ,String alias);
    }
}
