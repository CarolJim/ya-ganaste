package com.pagatodo.yaganaste.modules.emisor.PaymentToQR.operations.AgregarQRFisico;

public class AgregaQRContracts {


    public interface Listener{
        void onSuccessQRs();
        void onErrorQRs();
    }

    public interface Iteractor{
        void asociaQRValido(String Plate ,String alias);
        void validarQRValido(String Plate ,String alias);
    }
}
