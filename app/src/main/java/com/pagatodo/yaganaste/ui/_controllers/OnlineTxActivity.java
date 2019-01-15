package com.pagatodo.yaganaste.ui._controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.Menu;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ErrorObject;
import com.pagatodo.yaganaste.data.dto.OnlineTxData;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.freja.Errors;
import com.pagatodo.yaganaste.interfaces.DialogDoubleActions;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.onlinetx.controllers.OnlineTxView;
import com.pagatodo.yaganaste.ui.onlinetx.fragments.TxApprovedFragment;
import com.pagatodo.yaganaste.ui.onlinetx.fragments.TxDetailsFragment;
import com.pagatodo.yaganaste.ui.onlinetx.presenters.OnlineTxPresenter;
import com.pagatodo.yaganaste.utils.UI;

/**
 * @author Juan Guerra on 11/04/2017.
 */

public class OnlineTxActivity extends LoaderActivity implements OnEventListener, OnlineTxView {

    public static final String DATA = "data";

    public static final String EVENT_TX_APROVED = "1";
    public static final String EVENT_TX_NOT_APROVED = "2";
    public static final String EVENT_APROVE_TX = "3";

    private OnlineTxPresenter onlineTxPresenter;
    private String idToAprove;
    private boolean isAproving;
    private String shaPWD;


    public static Intent createIntent(Context from, String idFreja) {
        Intent intent = new Intent(from, OnlineTxActivity.class);
        Bundle extras = new Bundle();
        extras.putString(DATA, idFreja);
        intent.putExtras(extras);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_tx);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getString(DATA) != null) {
            onlineTxPresenter = new OnlineTxPresenter(this, extras.getString(DATA));
            onlineTxPresenter.getTransactions();
        } else {
            throw new IllegalCallException("To call " + this.getClass().getSimpleName() +
                    " you should pass as extra's parameters OnlineTxActivity");
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        setVisibilityPrefer(false);
    }

    @Override
    public boolean requiresTimer() {
        return false;
    }

    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        switch (event) {
            case EVENT_APROVE_TX:
                isAproving = true;
                shaPWD = data.toString();
                onlineTxPresenter.aproveTransaction(idToAprove, data.toString());
                break;

            case EVENT_TX_APROVED:
                loadFragment(TxApprovedFragment.newInstance(), Direction.FORDWARD);
                break;

            case EVENT_TX_NOT_APROVED:
                loadFragment(TxApprovedFragment.newInstance(false, data.toString()), Direction.FORDWARD);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }


    @Override
    public void onTxAproved() {
        onEvent(OnlineTxActivity.EVENT_TX_APROVED, null);
    }

    @Override
    public void onTxFailed(String message) {
        onEvent(EVENT_TX_NOT_APROVED, message);
    }

    @Override
    public void loadTransactionData(OnlineTxData data) {
        idToAprove = data.getIdFreja();
        loadFragment(TxDetailsFragment.newInstance(data));
    }

    @Override
    public void showError(final ErrorObject error) {
        UI.createSimpleCustomDialog("", error.getErrorMessage(), getSupportFragmentManager(), error.getErrorActions(), error.hasConfirm(), error.hasCancel());
    }
}