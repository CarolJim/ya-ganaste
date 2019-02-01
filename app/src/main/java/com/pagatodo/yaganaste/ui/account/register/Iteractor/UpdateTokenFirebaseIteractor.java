package com.pagatodo.yaganaste.ui.account.register.Iteractor;

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
import com.pagatodo.yaganaste.modules.management.request.UpdateTokenFirebase;
import com.pagatodo.yaganaste.ui.account.register.interfaces.ActualizarToken;

import org.json.JSONException;
import org.json.JSONObject;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.LINKED_QR;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.UPDATEFIREBASETOKEN;
import static com.pagatodo.yaganaste.utils.Recursos.CLABE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.TOKEN_FIREBASE;
import static com.pagatodo.yaganaste.utils.Recursos.TOKEN_FIREBASE_SESSION;
import static com.pagatodo.yaganaste.utils.Recursos.URL_FRIGGS;
import static com.pagatodo.yaganaste.utils.Recursos.URL_ODIN;

public class UpdateTokenFirebaseIteractor implements ActualizarToken.Iteractor , ListenerFriggs {


    ActualizarToken.Listener listener;
    private RequestQueue requestQueue;
    Context context ;


    public UpdateTokenFirebaseIteractor(ActualizarToken.Listener listener,  Context context) {
        this.listener = listener;
        this.requestQueue = Volley.newRequestQueue(App.getContext());
        this.context = context;
    }

    @Override
    public void updateTokenFirebase() {

        ApisFriggs apisFriggs = new ApisFriggs(this);
        UpdateTokenFirebase qrRequest = new UpdateTokenFirebase("YaGanaste","Android", ""+App.getInstance().getPrefs().loadData(TOKEN_FIREBASE));
        requestQueue.add(apisFriggs.sendRequest(FrigsMethod.POST,URL_ODIN +
                        App.getContext().getResources().getString(R.string.updateTokenFirebase),
                FriggsHeaders.getHeadersBasic(),qrRequest,UPDATEFIREBASETOKEN));
    }






    @Override
    public void onSuccess(WebService webService, JSONObject response) throws JSONException {
        listener.onSuccessToken();
    }

    @Override
    public void onError() {
        listener.onErrorToken();
    }



}
