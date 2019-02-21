package com.pagatodo.yaganaste.modules.emisor.PaymentToQR;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.QRUser;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.modules.data.QrItems;
import com.pagatodo.yaganaste.modules.management.ApisFriggs;
import com.pagatodo.yaganaste.modules.management.apis.FriggsHeaders;
import com.pagatodo.yaganaste.modules.management.apis.FrigsMethod;
import com.pagatodo.yaganaste.modules.management.apis.ListenerFriggs;
import com.pagatodo.yaganaste.modules.management.request.QrRequest;
import com.pagatodo.yaganaste.modules.management.response.QrDataResponse;
import com.pagatodo.yaganaste.modules.management.response.QrsResponse;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.DELETE_QR;
import static com.pagatodo.yaganaste.utils.Recursos.CLABE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.URL_FRIGGS;


public class QrManagerIteractor implements QrManagerContracts.Iteractor, ListenerFriggs {

    private QrManagerContracts.Listener listener;
    private RequestQueue requestQueue;

    public QrManagerIteractor(QrManagerContracts.Listener listener, Context context) {
        this.listener = listener;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    @Override
    public void getMyQrs() {
        ApisFriggs apisFriggs = new ApisFriggs(this);
        requestQueue.add(apisFriggs.sendRequest(FrigsMethod.GET,URL_FRIGGS +
                        App.getContext().getString(R.string.getQrs),
                FriggsHeaders.getHeadersBasic()));
    }

    @Override
    public void delQR(QrItems item) {
        ApisFriggs apisFriggs = new ApisFriggs(this);

        QrRequest qrRequest = new QrRequest(item.getQrUser().getPlate());
        requestQueue.add(apisFriggs.sendRequest(FrigsMethod.POST,URL_FRIGGS+
                App.getContext().getString(R.string.dltQRYG),FriggsHeaders.getHeadersBasic(), qrRequest,DELETE_QR));
    }

    @Override
    public void onSuccess(WebService webService, JSONObject response) {
        Gson gson = new Gson();
        QrsResponse qrResponse = gson.fromJson(response.toString(),QrsResponse.class);
        ArrayList<QrItems> list = new ArrayList<>();

       if (webService!=DELETE_QR) {

           for (QrDataResponse qrDataResponse : qrResponse.getData()) {
               Gson gsondata = new Gson();
               String jsonString = gsondata.toJson(qrDataResponse.getQr());
               list.add(new QrItems(new QRUser(qrDataResponse.getName(),
                       qrDataResponse.getQr().getAux().getPl()), jsonString, 2));
           }
           listener.onSuccessQRs(list);
       }else {
           listener.onSuccesDel();
       }
    }

    @Override
    public void onError() {
        listener.onErrorQRs();
    }
}
