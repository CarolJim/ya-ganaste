package com.pagatodo.yaganaste.modules.qr.operations.AgregarQRFisico;

import android.content.Context;

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

import static com.pagatodo.yaganaste.interfaces.enums.WebService.LINKED_QR;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDATE_PLATE;
import static com.pagatodo.yaganaste.utils.Recursos.CLABE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.URL_FRIGGS;

public class AgregaQRIteractor  implements  AgregaQRContracts.Iteractor, ListenerFriggs {


    private AgregaQRContracts.Listener listener;
    private RequestQueue requestQueue;
    private String plate;
    private String alias;

    Context context ;

    public AgregaQRIteractor(AgregaQRContracts.Listener iteractor, Context context) {
        this.listener = iteractor;
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(App.getContext());
    }

    @Override
    public void asociaQRValido(String plate ,String alias) {
        ApisFriggs apisFriggs = new ApisFriggs(this);
        QrRequest qrRequest = new QrRequest(plate,alias,"148", App.getInstance().getPrefs()
                .loadData(CLABE_NUMBER).replace(" ",""));
        requestQueue.add(apisFriggs.sendRequest(FrigsMethod.POST,URL_FRIGGS +
                        App.getContext().getResources().getString(R.string.linkedQr),
                FriggsHeaders.getHeadersBasic(),qrRequest,LINKED_QR));
    }

    @Override
    public void validarQRValido(String plate, String alias) {
        this.plate = plate;
        this.alias = alias;
        ApisFriggs apisFriggs = new ApisFriggs(this);
        QrRequest qrRequest = new QrRequest(plate,alias,"148", App.getInstance().getPrefs()
                .loadData(CLABE_NUMBER).replace(" ",""));
        requestQueue.add(apisFriggs.sendRequest(FrigsMethod.POST,URL_FRIGGS +
                        App.getContext().getResources().getString(R.string.validateQr),
                FriggsHeaders.getHeadersBasic(),qrRequest,VALIDATE_PLATE));

    }

    @Override
    public void onSuccess(WebService webService, JSONObject response) {
        switch (webService){
            case VALIDATE_PLATE:
                asociaQRValido(plate,alias);
                break;
            case LINKED_QR:
                listener.onSuccessQRs();
                break;
        }
    }

    @Override
    public void onError() {
        listener.onErrorQRs();
    }
}
