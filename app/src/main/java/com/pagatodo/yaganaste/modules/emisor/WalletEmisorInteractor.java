package com.pagatodo.yaganaste.modules.emisor;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.BloquearCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerBancoBinRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.ConsultaAsignacionTarjetaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.trans.ConsultarTitularCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerBancoBinResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarAsignacionTarjetaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.ConsultarTitularCuentaResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.modules.management.ApisFriggs;
import com.pagatodo.yaganaste.modules.management.apis.FriggsHeaders;
import com.pagatodo.yaganaste.modules.management.apis.FrigsMethod;
import com.pagatodo.yaganaste.modules.management.apis.ListenerFriggs;
import com.pagatodo.yaganaste.modules.management.request.QrRequest;
import com.pagatodo.yaganaste.modules.management.response.QrValidateResponse;
import com.pagatodo.yaganaste.modules.management.response.QrsResponse;
import com.pagatodo.yaganaste.modules.management.singletons.NotificationSingleton;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.ApiTrans;
import com.pagatodo.yaganaste.utils.Recursos;

import org.json.JSONException;
import org.json.JSONObject;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_ASIGNACION_TARJETA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.CONSULTAR_TITULAR_CUENTA;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.DATA_QR;
import static com.pagatodo.yaganaste.utils.Recursos.CLABE_NUMBER;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.URL_FRIGGS;

public class WalletEmisorInteractor implements WalletEmisorContracts.Interactor, ListenerFriggs {

    public static final int BLOQUEO = 1;
    public static final int DESBLOQUEO = 2;
    private WalletEmisorContracts.Listener listener;
    private RequestQueue requestQueue;

    public WalletEmisorInteractor(WalletEmisorContracts.Listener listener, Context context) {
        this.listener = listener;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    @Override
    public void TemporaryBlock() {
        this.listener.showLoad();
        BloquearCuentaRequest bloquearCuentaRequest = new BloquearCuentaRequest("" + BLOQUEO);
        try {
            ApiTrans.bloquearCuenta(bloquearCuentaRequest, this);
        } catch (OfflineException e) {
            // e.printStackTrace();
            //this.listener.onErrorTemporaryBlock();
        }
    }



    @Override
    public void onFailed(DataSourceResult error) {
        this.listener.hideLoad();
        if (error != null && error.getWebService() == CONSULTAR_ASIGNACION_TARJETA) {
          this.listener.onErrorRequest(error.getData().toString());
        }
    }

    @Override
    public void validateCard(String cardNumber) {
        this.listener.showLoad();
        ConsultaAsignacionTarjetaRequest request = new ConsultaAsignacionTarjetaRequest(cardNumber);
        try {
            ApiTrans.consultaAsignacionTarjeta(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            this.listener.onErrorRequest(App.getContext().getString(R.string.no_internet_access));
            //accountManager.onError(CONSULTAR_ASIGNACION_TARJETA, App.getContext().getString(R.string.no_internet_access);
        }
    }

    @Override
    public void valideteQR(String plate) {
        ApisFriggs apisFriggs = new ApisFriggs(this);
        NotificationSingleton.getInstance().getRequest().setPlate(plate);
        QrRequest qrRequest = new QrRequest(plate,"","148", App.getInstance().getPrefs()
                .loadData(CLABE_NUMBER).replace(" ",""));
        /*requestQueue.add(apisFriggs.sendRequest(FrigsMethod.POST,URL_FRIGGS +
                        App.getContext().getString(R.string.getDataQRYG),
                FriggsHeaders.getHeadersBasic()));*/
        requestQueue.add(apisFriggs.sendRequest(FrigsMethod.POST,URL_FRIGGS +
                        App.getContext().getResources().getString(R.string.getDataQRYG),
                FriggsHeaders.getHeadersBasic(),qrRequest,DATA_QR));

    }

    //FRIGGS
    @Override
    public void onSuccess(WebService webService, JSONObject response) throws JSONException {
        if (webService == DATA_QR){
            Gson gson = new Gson();
            QrValidateResponse QRresponse = gson.fromJson(response.toString(),QrValidateResponse.class);
            //String jsonString = gsondata.toJson(qrDataResponse.getQr());
            Log.d("DATA QR", QRresponse.getData().getPlate());
            listener.onSouccesDataQR(QRresponse);
        }
    }

    @Override
    public void onError() {
        listener.onErrorRequest("QR INVALIDO");
    }

    @Override
    public void getTitular(String cuenta) {
        listener.showLoad();
        ConsultarTitularCuentaRequest request = new ConsultarTitularCuentaRequest();
        request.setCuenta(cuenta.replaceAll(" ",""));
        try {
            ApiTrans.consultarTitularCuenta(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            //listener.onError(CONSULTAR_TITULAR_CUENTA, App.getContext().getString(R.string.no_internet_access));
            listener.hideLoad();
            listener.onErrorRequest(App.getContext().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void getDataBank(String bin, String cob) {
        listener.showLoad();
        try {
            ObtenerBancoBinRequest request = new ObtenerBancoBinRequest();
            request.setPbusqueda(bin);
            request.setTipoConsulta(cob);
            ApiAdtvo.obtenerBancoBin(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            listener.hideLoad();
            listener.onErrorRequest(App.getContext().getString(R.string.no_internet_access));
        }
    }

    //APIS
    @Override
    public void onSuccess(DataSourceResult result) {
        this.listener.hideLoad();
        if (result.getData() instanceof BloquearCuentaResponse) {
            BloquearCuentaResponse response = (BloquearCuentaResponse) result.getData();
            if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
                //Log.d("PreferUserIteractor", "BloquearCuentaResponse Sucess " + response.getMensaje());
                //preferUserPresenter.successGenericToPresenter(dataSourceResult);

            } else {
                //Log.d("PreferUserIteractor", "BloquearCuentaResponse Sucess with Error " + response.getMensaje());
                ///preferUserPresenter.errorGenericToPresenter(dataSourceResult);
            }
        } else if (result.getData() instanceof ConsultarAsignacionTarjetaResponse){
            if (((ConsultarAsignacionTarjetaResponse) result.getData()).getCodigoRespuesta() == CODE_OK){
                this.listener.onSouccesValidateCard();
            } else {
                this.listener.onErrorRequest(((ConsultarAsignacionTarjetaResponse) result.getData()).getMensaje());
            }
        } else if (result.getData() instanceof ConsultarTitularCuentaResponse){
            ConsultarTitularCuentaResponse data = (ConsultarTitularCuentaResponse) result.getData();
            if (data.getAccion() == CODE_OK) {
                listener.onSouccesGetTitular(data);
            } else {
                listener.onErrorRequest(data.getMensaje());
            }
        } else if (result.getData() instanceof ObtenerBancoBinResponse){
            ObtenerBancoBinResponse response = (ObtenerBancoBinResponse) result.getData();
            if (response.getCodigoRespuesta() == CODE_OK) {
                try {
                    if (response.getData() != null) {
                        listener.onSouccessgetgetDataBank(response);
                            //listener.setDataBank(response.getData().getIdComercioAfectado(), response.getData().getNombre());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onErrorRequest(response.getMensaje());
                }
            } else {
                listener.onErrorRequest(App.getContext().getString(R.string.no_internet_access));
            }
        }
    }


}
