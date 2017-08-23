package com.pagatodo.yaganaste.ui.onlinetx.presenters;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.dto.OnlineTxData;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.freja.transactions.presenter.TransactionPresenterAbs;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.enums.OnlineTypes;
import com.pagatodo.yaganaste.ui.onlinetx.controllers.OnlineTxView;
import com.pagatodo.yaganaste.utils.Codec;
import com.verisec.freja.mobile.core.wsHandler.beans.transaction.response.FmcTransactionResponse;
import com.verisec.freja.mobile.core.wsHandler.beans.transaction.response.fromV1_5.FmcTransactionListResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @author Juan Guerra on 18/04/2017.
 */

public class OnlineTxPresenter extends TransactionPresenterAbs {

    private static final String TAG = OnlineTxPresenter.class.getName();

    private String idFreja;
    private OnlineTxView onlineTxView;
    private Context context;
    private int intents;
    private final int max = 3;

    public OnlineTxPresenter(OnlineTxView onlineTxView, String idFreja) {
        super(App.getInstance());
        this.context = App.getInstance();
        this.idFreja = idFreja;
        this.onlineTxView = onlineTxView;
    }

    @Override
    public void getTransactions() {
        onlineTxView.showLoader(context.getString(R.string.verifying_transaction));
        super.getTransactions();
    }

    @Override
    public void aproveTransaction(String idFreja, String nip) {
        intents++;
        onlineTxView.showLoader(context.getString(R.string.aproving_transaction));
        super.aproveTransaction(idFreja, nip);
    }

    @Override
    public void setTransactions(FmcTransactionListResponse txs) {
        onlineTxView.hideLoader();
        intents = 0;
        List<FmcTransactionResponse> transactions = txs.getTransactions();
        Gson deserializer = new GsonBuilder().registerTypeAdapter(OnlineTypes.class, new OnlineTypes.Deserializer())
                .create();

        JSONObject toDeserialize;
        OnlineTxData data = null;
        for (FmcTransactionResponse transaction : transactions) {
            try {
                if (Codec.applyCRC32(transaction.getTransactionReference()).equalsIgnoreCase(idFreja)) {
                    toDeserialize = new JSONObject(transaction.getTransactionText());
                    toDeserialize.put("idFreja", transaction.getTransactionReference());
                    data = deserializer.fromJson(toDeserialize.toString(), OnlineTxData.class);
                    break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (data == null) {
            onError(Errors.NO_PENDING_TRANSACTIONS);
        } else {
            onlineTxView.loadTransactionData(data);
        }
    }

    @Override
    public void onTransactionAproved() {
        onlineTxView.hideLoader();
        onlineTxView.onTxAproved();
    }

    @Override
    public void onError(final Errors error) {

        onlineTxView.hideLoader();
        if (error.allowsReintent() && intents < max) {
            DialogDoubleActions actions = new DialogDoubleActions() {
                @Override
                public void actionConfirm(Object... params) {

                }

                @Override
                public void actionCancel(Object... params) {
                    onlineTxView.onTxFailed(App.getInstance().getString(R.string.operacion_cancelada_usuario));
                }
            };

            ErrorObject.Builder builder = new ErrorObject.Builder().setMessage(error.getMessage())
                    .setHasCancel(error.allowsReintent()).setActions(actions);
            onlineTxView.showError(builder.build());
        } else if (intents >= max){
            onlineTxView.onTxFailed(App.getInstance().getString(R.string.intentos_excedidos));
        } else {
            onlineTxView.onTxFailed(error.getMessage());
        }
    }
}