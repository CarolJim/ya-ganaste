package com.pagatodo.yaganaste.modules.qr.operations.AgregarQRVirtual;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.modules.management.ApisFriggs;
import com.pagatodo.yaganaste.modules.management.apis.FriggsHeaders;
import com.pagatodo.yaganaste.modules.management.apis.FrigsMethod;
import com.pagatodo.yaganaste.modules.management.apis.ListenerFriggs;
import com.pagatodo.yaganaste.modules.management.request.QrRequest;

import org.json.JSONObject;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.NEW_QR;
import static com.pagatodo.yaganaste.utils.Recursos.CLABE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.URL_FRIGGS;

public class AgregarVirtQRIteractor implements  AgregarVirtContracts.Iteractor, ListenerFriggs {

    private AgregarVirtContracts.Listener listener;
    private RequestQueue requestQueue;

    AgregarVirtQRIteractor(AgregarVirtContracts.Listener iteractor) {
        this.listener = iteractor;
        this.requestQueue = Volley.newRequestQueue(App.getContext());
    }

    @Override
    public void validarQRValido(String alias) {
        ApisFriggs apisFriggs = new ApisFriggs(this);
        QrRequest qrRequest = new QrRequest("",alias,"148", App.getInstance().getPrefs()
                .loadData(CLABE_NUMBER).replace(" ",""));
        requestQueue.add(apisFriggs.sendRequest(FrigsMethod.POST,URL_FRIGGS +
                        App.getContext().getResources().getString(R.string.newQr),
                FriggsHeaders.getHeadersBasic(),qrRequest,NEW_QR));
    }

    @Override
    public void onSuccess(WebService webService, JSONObject response) {
        listener.onSuccessQRs();
    }

    @Override
    public void onError() {
        listener.onErrorQRs();
    }
}
