package com.pagatodo.yaganaste.modules.paymentSuccess;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.modules.management.ApisFriggs;
import com.pagatodo.yaganaste.modules.management.apis.FriggsHeaders;
import com.pagatodo.yaganaste.modules.management.apis.FrigsMethod;
import com.pagatodo.yaganaste.modules.management.request.NotificationPayRequest;

import org.json.JSONException;
import org.json.JSONObject;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.NOTIFICATION;
import static com.pagatodo.yaganaste.utils.Recursos.URL_FRIGGS;

public class PaymentSuccessInteractor implements PaymentSuccessContracts.Interactor{

    private PaymentSuccessContracts.Listener listener;
    private RequestQueue requestQueue;

    public PaymentSuccessInteractor(PaymentSuccessContracts.Listener listener, Context context) {
        this.listener = listener;
        this.requestQueue = Volley.newRequestQueue(context);

    }

    @Override
    public void paymentNotification(String plate, String amount, String concept) {
        ApisFriggs apisFriggs = new ApisFriggs(this);
        NotificationPayRequest request = new NotificationPayRequest(plate,amount,concept);
        requestQueue.add(apisFriggs.sendRequest(FrigsMethod.POST,URL_FRIGGS +
                        App.getContext().getResources().getString(R.string.ntfyMrchntYG),
                FriggsHeaders.getHeadersBasic(),request,NOTIFICATION));
    }

    @Override
    public void onSuccess(WebService webService, JSONObject response) throws JSONException {
        if (webService == NOTIFICATION){
            listener.succesNotification();
        } else {
            listener.onError("");
        }
    }

    @Override
    public void onError() {
        listener.onError("No se pudo realizar la notificación");
    }
}
