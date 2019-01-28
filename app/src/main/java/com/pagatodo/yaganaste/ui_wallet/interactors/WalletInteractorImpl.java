package com.pagatodo.yaganaste.ui_wallet.interactors;


import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.Giros;
import com.pagatodo.yaganaste.data.model.RegisterAgent;
import com.pagatodo.yaganaste.data.model.SubGiro;
import com.pagatodo.yaganaste.data.model.webservice.request.adq.SaldoRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.EstatusCuentaRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.starbucks.CardRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.ObtenerInfoComercioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.ConsultaSaldoCupoResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.starbucks.SaldoSBRespons;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Agentes;
import com.pagatodo.yaganaste.data.room_db.entities.Operadores;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdq;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.ApiStarbucks;
import com.pagatodo.yaganaste.net.ApiTrans;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WalletInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.WalletNotification;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.pagatodo.yaganaste.ui_wallet.fragments.WalletTabFragment.ERROR_STATUS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_ADQ;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_BUSINESS;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_EMISOR;
import static com.pagatodo.yaganaste.ui_wallet.pojos.ElementWallet.TYPE_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.CONNECTION_TYPE;
import static com.pagatodo.yaganaste.utils.Recursos.EVENT_BALANCE_ADQ;
import static com.pagatodo.yaganaste.utils.Recursos.EVENT_BALANCE_EMISOR;
import static com.pagatodo.yaganaste.utils.Recursos.EVENT_EDIT_FAV;
import static com.pagatodo.yaganaste.utils.Recursos.IS_UYU;
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
        SaldoRequest saldoRequest = new SaldoRequest();
        DatabaseManager db = new DatabaseManager();
        try {
            switch (typeWallet) {
                case TYPE_EMISOR:
                    ApiTrans.consultarSaldo(this);
                    break;
                case TYPE_ADQ:
                     try {
                        Operadores operador = db.getOperadoresAdmin(agente);
                        saldoRequest.addPetroNum(new SaldoRequest.PetroNum(operador.getPetroNumero()));
                        RequestHeaders.setIdCuentaAdq(operador.getIdUsuarioAdquirente());
                        App.getInstance().getPrefs().saveDataBool(IS_UYU, agente.isEsComercioUYU());
                        ApiAdq.consultaSaldoCupo(saldoRequest, this);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case TYPE_STARBUCKS:
                    String numCard = App.getInstance().getPrefs().loadData(NUMBER_CARD_STARBUCKS);
                    CardRequest cardRequest = new CardRequest(numCard);
                    ApiStarbucks.saldoSb(cardRequest, this);
                    break;
                case TYPE_BUSINESS:
                    //getOperadores
                    try {
                        if (!db.getOperadores().isEmpty()) {
                            for (Operadores operador : db.getOperadores()) {
                                if (operador != null && operador.getIsAdmin()) {
                                    saldoRequest.addPetroNum(new SaldoRequest.PetroNum(operador.getPetroNumero()));
                                }
                            }
                            ApiAdq.consultaSaldoAdmin(saldoRequest, this);
                        } else {
                            this.listener.onSuccesSaldo(typeWallet, "");
                        }
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


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

    @Override
    public void getEstatusDocumentos() {
        try {
            ApiAdtvo.obtenerDocumentos(this);
        } catch (OfflineException e) {
            this.listener.onFailed(Recursos.CODE_OFFLINE, Recursos.NO_ACTION, App.getInstance().getString(R.string.no_internet_access));
        }
    }

    @Override
    public void getInfoComercio(String folio) {
        try {
            ApiAdtvo.obtenerInfoComercio(folio, this);
        } catch (OfflineException e) {
            this.listener.onFailed(Recursos.CODE_OFFLINE, Recursos.NO_ACTION, App.getInstance().getString(R.string.no_internet_access));
        }
    }

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
                Bundle bundle = new Bundle();
                bundle.putString(CONNECTION_TYPE, Utils.getTypeConnection());
                FirebaseAnalytics.getInstance(App.getContext()).logEvent(EVENT_BALANCE_EMISOR, bundle);
                JSONObject props = new JSONObject();
                if(!BuildConfig.DEBUG) {
                    try {
                        props.put(CONNECTION_TYPE, Utils.getTypeConnection());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    App.mixpanel.track(EVENT_BALANCE_EMISOR, props);
                }
                this.listener.onSuccess(TYPE_EMISOR, result.getData());
                break;
            case CONSULTAR_SALDO_ADQ:
                Bundle bundleFb = new Bundle();
                bundleFb.putString(CONNECTION_TYPE, Utils.getTypeConnection());
                FirebaseAnalytics.getInstance(App.getContext()).logEvent(EVENT_BALANCE_ADQ, bundleFb);
                JSONObject propsFb = new JSONObject();
                if(!BuildConfig.DEBUG) {
                    try {
                        propsFb.put(CONNECTION_TYPE, Utils.getTypeConnection());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    App.mixpanel.track(EVENT_BALANCE_ADQ, propsFb);
                }
                this.listener.onSuccesSaldo(TYPE_ADQ, ((ConsultaSaldoCupoResponse) result.getData()).getSaldo());
                break;
            case CONSULTAR_SALDO_ADQ_ADM:
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
            case OBTENER_DOCUMENTOS:
                ObtenerDocumentosResponse data = (ObtenerDocumentosResponse) result.getData();
                if (data.getCodigoRespuesta() == CODE_OK) {
                    List<EstatusDocumentosResponse> listaDocumentos = data.getDocumentos();
                    if ((listaDocumentos != null && listaDocumentos.size() > 0) || data.getIdEstatus() != 0) {
                        this.listener.onSuccessResponse(data);
                    } else {
                        this.listener.onFailed(ERROR_STATUS, Recursos.NO_ACTION, "No se pudo recuperar el estatus de la documentación");
                    }
                } else {
                    this.listener.onFailed(ERROR_STATUS, Recursos.NO_ACTION, data.getMensaje());
                }
                break;
            case INFO_COMERCIO:
                ObtenerInfoComercioResponse response = (ObtenerInfoComercioResponse) result.getData();
                if (response.getCodigoRespuesta() == CODE_OK) {
                    RegisterAgent.getInstance().setCodigoPostal(response.getDomicilioNegocio().getCp());
                    RegisterAgent.getInstance().setCalle(response.getDomicilioNegocio().getCalle());
                    RegisterAgent.getInstance().setIdColonia(response.getDomicilioNegocio().getIdColonia());
                    RegisterAgent.getInstance().setIdEstado(response.getDomicilioNegocio().getIdEstado());
                    RegisterAgent.getInstance().setNumExterior(response.getDomicilioNegocio().getNumeroExterior());
                    RegisterAgent.getInstance().setNumInterior(response.getDomicilioNegocio().getNumeroInterior());
                    Giros giro = new Giros();
                    giro.setIdGiro(response.getGiro());
                    RegisterAgent.getInstance().setGiro(giro);
                    RegisterAgent.getInstance().setNombre(response.getNombreNegocio());
                    if (response.getNumeroTelefono().length() == 13) {
                        RegisterAgent.getInstance().setLada(response.getNumeroTelefono().substring(0, 3));
                        RegisterAgent.getInstance().setTelefono(response.getNumeroTelefono().substring(3, response.getNumeroTelefono().length()));
                    } else {
                        RegisterAgent.getInstance().setLada(response.getNumeroTelefono().substring(0, 2));
                        RegisterAgent.getInstance().setTelefono(response.getNumeroTelefono().substring(2, response.getNumeroTelefono().length()));
                    }
                    SubGiro subGiro = new SubGiro();
                    subGiro.setIdSubgiro(response.getSubGiro());
                    RegisterAgent.getInstance().setSubGiros(subGiro);
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
            case OBTENER_DOCUMENTOS:
                this.listener.onFailed(0, Recursos.NO_ACTION, error.getData().toString());
                break;
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
