package com.pagatodo.yaganaste.modules.emisor.PaymentToQR.operations.AgregarQRVirtual;

public class AgregarVirtContracts {


    public interface Listener{
        void onSuccessQRs();
        void onErrorQRs();
    }

    public interface Iteractor{
        void asociaQRValido(String Plate ,String alias);
        void validarQRValido(String alias);
    }
}
