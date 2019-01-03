package com.pagatodo.yaganaste.modules.wallet_emisor.PaymentToQR.operations.EditQr;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.modules.data.QrItems;
import com.pagatodo.yaganaste.modules.management.ApisFriggs;
import com.pagatodo.yaganaste.modules.management.apis.FriggsHeaders;
import com.pagatodo.yaganaste.modules.management.apis.FrigsMethod;
import com.pagatodo.yaganaste.modules.management.apis.ListenerFriggs;
import com.pagatodo.yaganaste.modules.management.request.QrRequest;

import org.json.JSONObject;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.UPDATE_QR;
import static com.pagatodo.yaganaste.utils.Recursos.CLABE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.URL_FRIGGS;

public class EditQrInteractor implements EditQrContracts.Interactor, ListenerFriggs {

    private EditQrContracts.Listener listener;
    private RequestQueue requestQueue;

    EditQrInteractor(EditQrContracts.Listener listener) {
        this.listener = listener;
        this.requestQueue = Volley.newRequestQueue(App.getContext());
    }

    @Override
    public void onEditQr(QrItems item) {
        ApisFriggs apisFriggs = new ApisFriggs(this);
        QrRequest qrRequest = new QrRequest(item.getQrUser().getPlate()
                ,item.getQrUser().getAlias(),"148", App.getInstance().getPrefs()
                .loadData(CLABE_NUMBER).replace(" ",""));
        requestQueue.add(apisFriggs.sendRequest(FrigsMethod.POST,URL_FRIGGS +
                        App.getContext().getResources().getString(R.string.updateQR),
                FriggsHeaders.getHeadersBasic(),qrRequest,UPDATE_QR));

    }

    @Override
    public void onSuccess(WebService webService, JSONObject response) {
        listener.onSuccessEdit();
    }

    @Override
    public void onError() {
        listener.onErrorEdit("No se pudo hacer la operación");
    }
}
