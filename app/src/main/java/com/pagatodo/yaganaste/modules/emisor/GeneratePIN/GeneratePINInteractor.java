package com.pagatodo.yaganaste.modules.emisor.GeneratePIN;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.modules.management.request.CardRemplaceRequest;
import com.pagatodo.yaganaste.modules.management.request.CardRequest;
import com.pagatodo.yaganaste.net.ApiTrans;
import com.pagatodo.yaganaste.net.NetFacade;

import java.util.HashMap;
import java.util.Map;

import static com.pagatodo.yaganaste.interfaces.enums.HttpMethods.METHOD_POST;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.BLOQUEO_TARJETA_DEFINITIVO;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.REMPLAZAR_TARJETA_DIRECTO;

public class GeneratePINInteractor implements GeneratePINContracts.Interactor, IRequestResult {

    private GeneratePINContracts.Listener listener;

    GeneratePINInteractor(GeneratePINContracts.Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onBlockCard(String numberCard) {
        this.listener.onShowLoading();

        try {
            CardRequest cardRequest = new CardRequest(numberCard);
            //ApiTrans.asignarNip(cardRequest, this, ASIGNAR_NIP);
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-type", "application/json");
            NetFacade.consumeWS(BLOQUEO_TARJETA_DEFINITIVO,
                    METHOD_POST, ApiTrans.getUrlServerTrans() + App.getContext().getString(R.string.bloqueoTarjetaDifinitivo),
                    headers, cardRequest, null, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            //accountManager.onError(ASIGNAR_NIP, App.getContext().getString(R.string.no_internet_access));
            this.listener.onFail(App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void onRemplaceCard(String currentCard, String newCard) {
        this.listener.onShowLoading();
        try {

            CardRemplaceRequest remplaceRequest = new CardRemplaceRequest(currentCard,newCard);
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-type", "application/json");

            NetFacade.consumeWS(REMPLAZAR_TARJETA_DIRECTO,
                    METHOD_POST, ApiTrans.getUrlServerTrans() + App.getContext().getString(R.string.reemplazarTarjetaDirecto),
                    headers, remplaceRequest, null, this);

        } catch (OfflineException e) {
            e.printStackTrace();
            //accountManager.onError(ASIGNAR_NIP, App.getContext().getString(R.string.no_internet_access));
            this.listener.onHideLoading();
            this.listener.onFail(App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void onAsignNIP() {

    }

    @Override
    public void onSuccess(DataSourceResult result) {

        this.listener.onHideLoading();
        this.listener.onSucces();
    }

    @Override
    public void onFailed(DataSourceResult error) {
        this.listener.onHideLoading();
        this.listener.onFail(error.toString());
    }
}
