package com.pagatodo.yaganaste.ui_wallet.interactors;


import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EstatusCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.CardRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ConsultaSaldoCupoResponse;
import com.pagatodo.yaganaste.data.room_db.entities.Agentes;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.starbucks.SaldoSBRespons;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdq;
import com.pagatodo.yaganaste.net.ApiStarbucks;
import com.pagatodo.yaganaste.net.ApiTrans;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WalletInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WalletNotification;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import ly.count.android.sdk.Countly;

import static com.pagatodo.yaganaste.ui_wallet.fragments.WalletTabFragment.ERROR_STATUS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_EMISOR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.CONNECTION_TYPE;
import static com.pagatodo.yaganaste.utils.Recursos.EVENT_BALANCE_ADQ;
import static com.pagatodo.yaganaste.utils.Recursos.EVENT_BALANCE_EMISOR;
import static com.pagatodo.yaganaste.utils.Recursos.NUMBER_CARD_STARBUCKS;

/**
 * Created by icruz on 12/12/2017.
 */

public class WalletInteractorImpl implements WalletInteractor {

    private WalletNotification listener;

    public WalletInteractorImpl(WalletNotification listener) {
        this.listener = listener;
    }

    @Override
    public void getWalletsCards(final boolean error, final WalletNotification listener) {
        listener.onSuccess(error);
    }

    @Override
    public void getBalance(int typeWallet, Agentes agente) {
        try {
            switch (typeWallet) {
                case TYPE_EMISOR:
                    if (!BuildConfig.DEBUG) {
                        Countly.sharedInstance().startEvent(EVENT_BALANCE_EMISOR);
                    }
                    ApiTrans.consultarSaldo(this);
                    break;
                case TYPE_ADQ:
                    if (!BuildConfig.DEBUG) {
                        Countly.sharedInstance().startEvent(EVENT_BALANCE_ADQ);
                    }
                    ApiAdq.consultaSaldoCupo(this, agente);
                    break;
                case TYPE_STARBUCKS:
                    String numCard = App.getInstance().getPrefs().loadData(NUMBER_CARD_STARBUCKS);
                    CardRequest cardRequest = new CardRequest(numCard);
                    ApiStarbucks.saldoSb(cardRequest, this);
                    break;
                default:
                    this.listener.onSuccesSaldo(typeWallet, "");
                    break;
            }

        } catch (OfflineException e) {
            e.printStackTrace();
            this.listener.onFailed(Recursos.CODE_OFFLINE, Recursos.NO_ACTION, App.getInstance().getString(R.string.no_internet_access));
        }
    }


    @Override
    public void getStatusAccount(EstatusCuentaRequest request) {
        try {
            ApiTrans.estatusCuenta(request, this);
        } catch (OfflineException e) {
            e.printStackTrace();
            this.listener.onFailed(Recursos.CODE_OFFLINE, Recursos.NO_ACTION, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    /*@Override
    public void getInfoAgente() {
        try {
            ApiAdtvo.getInformacionAgente(this);
        } catch (OfflineException e) {
            e.printStackTrace();
            listener.onFailed(Recursos.CODE_OFFLINE, Recursos.NO_ACTION, App.getInstance().getString(R.string.no_internet_access));
        }
    }*/

    //REQUEST
    @Override
    public void onSuccess(DataSourceResult result) {
        switch (result.getWebService()) {
            /*case GET_INFORMACION_AGENTE:
                if (result.getData() instanceof InformacionAgenteResponse) {
                    InformacionAgenteResponse response = (InformacionAgenteResponse) result.getData();
                    if (response.getCodigoRespuesta() == CODE_OK) {
                        this.listener.onSuccess(null, response.getData());
                    } else {
                        this.listener.onFailed(Recursos.CODE_ERROR_INFO_AGENTE, Recursos.NO_ACTION, response.getMensaje());
                    }
                }
                break;*/
            case CONSULTAR_SALDO:
                //this.listener.onSuccesSaldo(TYPE_EMISOR, ((ConsultarSaldoResponse) result.getData()).getData().getSaldo());
                if (!BuildConfig.DEBUG) {
                    Map<String, String> segmentation = new HashMap<>();
                    segmentation.put(CONNECTION_TYPE, Utils.getTypeConnection());
                    Countly.sharedInstance().endEvent(EVENT_BALANCE_EMISOR, segmentation, 1, 0);
                }
                this.listener.onSuccess(TYPE_EMISOR, result.getData());
                break;
            case CONSULTAR_SALDO_ADQ:
                if (!BuildConfig.DEBUG) {
                    Map<String, String> segmentation = new HashMap<>();
                    segmentation.put(CONNECTION_TYPE, Utils.getTypeConnection());
                    Countly.sharedInstance().endEvent(EVENT_BALANCE_ADQ, segmentation, 1, 0);
                }
                this.listener.onSuccesSaldo(TYPE_ADQ, ((ConsultaSaldoCupoResponse) result.getData()).getSaldo());
                break;
            case CONSULTAR_SALDO_SB:
                validateResponse((SaldoSBRespons) result.getData());
                break;
            case ESTATUS_CUENTA:
                if (result.getData() instanceof EstatusCuentaResponse) {
                    EstatusCuentaResponse response = (EstatusCuentaResponse) result.getData();
                    if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
                        this.listener.onSuccessResponse(response);
                    } else {
                        this.listener.onFailed(ERROR_STATUS, Recursos.NO_ACTION, response.getMensaje());
                    }
                }
                break;

        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        switch (error.getWebService()) {
           /* case GET_INFORMACION_AGENTE:
                this.listener.onFailed(CODE_ERROR_INFO_AGENTE, Recursos.NO_ACTION, error.getData().toString());
                break;*/
            case CONSULTAR_MOVIMIENTOS_MES:
                this.listener.onFailed(0, Recursos.NO_ACTION, error.getData().toString());
                break;
            case ESTATUS_CUENTA:
                this.listener.onFailed(ERROR_STATUS, Recursos.NO_ACTION, error.getData().toString());
                break;
        }

    }

    private void validateResponse(SaldoSBRespons response) {
        if (response.getRespuesta().getCodigoRespuesta() == Recursos.CODE_OK) {
            this.listener.onSuccesSaldo(TYPE_STARBUCKS, response.getSaldo());
        } else {
            this.listener.onFailedSaldo(response.getRespuesta().getMensaje());
        }
    }


}
