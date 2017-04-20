package com.pagatodo.yaganaste.ui._controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.OnlineTxData;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.onlinetx.fragments.TxApprovedFragment;
import com.pagatodo.yaganaste.ui.onlinetx.fragments.TxDetailsFragment;

import java.io.Serializable;

/**
 * @author Juan Guerra on 11/04/2017.
 */

public class OnlineTxActivity extends SupportFragmentActivity implements OnEventListener {

    public static final String DATA = "data";

    public static final String EVENT_TX_APROVED = "1";

    public static Intent createIntent(@NonNull Context from, OnlineTxData data) {
        Intent intent = new Intent(from, OnlineTxActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable(DATA, data);
        intent.putExtras(extras);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_tx);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getSerializable(DATA) != null) {
            Serializable data = extras.getSerializable(DATA);
            loadFragment(TxDetailsFragment.newInstance(data));
        } else {
            throw new IllegalCallException("To call " + this.getClass().getSimpleName() +
                    " you should pass as extra's parameters OnlineTxActivity");
        }
    }

    @Override
    public void onEvent(String event, Object data) {
        if (event.equals(EVENT_TX_APROVED)) {
            loadFragment(TxApprovedFragment.newInstance(), Direction.FORDWARD);
        }
    }

}