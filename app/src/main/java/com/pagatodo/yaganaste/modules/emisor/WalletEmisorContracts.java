package com.pagatodo.yaganaste.modules.emisor;

import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarTitularCuentaResponse;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.modules.management.response.QrValidateResponse;

public class WalletEmisorContracts {

    public interface Listener{
        void showLoad();
        void hideLoad();
        void onSouccesValidateCard();
        void onErrorRequest(String msj);
        void onSouccesDataQR(QrValidateResponse QRresponse);
        void onSouccesGetTitular(ConsultarTitularCuentaResponse data);
        void onSouccessgetgetDataBank();
    }

    interface Interactor extends IRequestResult {
        void TemporaryBlock();
        void validateCard(String cardNumber);
        void valideteQR(String plate);
        void getTitular(String cuenta);
        void getDataBank(String bin, String cob);
    }

    interface Router{
        void onShowActivatePhysicalCard();
        void onShowGeneratePIN();
        void onshowAccountStatus();
        void onShowMyVirtualCardAccount();
        void onShowMyChangeNip();
        void onShowEnvioFormulario(Envios envio);
        void onShowBlockCard();
        void onShowTemporaryBlock();
        void onShowCardActive();
    }
}
