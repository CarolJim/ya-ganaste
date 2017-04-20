package com.pagatodo.yaganaste.ui._controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.exceptions.NullObjectExcepcion;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.maintabs.fragments.DetailsAdquirenteFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.DetailsEmisorFragment;

import java.io.Serializable;

/**
 * @author Juan Guerra on 11/04/2017.
 */

public class DetailsActivity extends SupportFragmentActivity {

    public static final String DATA = "data";
    public static final String TYPE = "type";

    enum TYPES{
        EMISOR,
        ADQUIRENTE
    }

    public static Intent createIntent(@NonNull Context from, MovimientosResponse data) {
        return createIntent(from, TYPES.EMISOR, data);
    }

    public static Intent createIntent(@NonNull Context from, DataMovimientoAdq data) {
        return createIntent(from, TYPES.ADQUIRENTE, data);
    }

    public static Intent createIntent(@NonNull Context from, TYPES type, Serializable data) {
        Intent intent = new Intent(from, DetailsActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable(DATA, data);
        extras.putString(TYPE, type.toString());
        intent.putExtras(extras);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_em_adq);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getSerializable(DATA) != null
                && extras.getString(TYPE) != null) {
            Serializable serializable = extras.getSerializable(DATA);
            loadFragment(TYPES.valueOf(extras.getString(TYPE)), serializable);
        } else {
            throw new IllegalCallException("To call " + this.getClass().getSimpleName() +
                    " you should pass as extra's parameters type and DataMovimientoAdq or " +
                    "ResumenMovimientosAdqResponse");
        }
    }


    protected void loadFragment(TYPES type, Serializable data) {
        if (type != null) {
            if (type.equals(TYPES.EMISOR)) {
                loadFragment(DetailsEmisorFragment.newInstance((MovimientosResponse) data));
            } else if (type.equals(TYPES.ADQUIRENTE)) {
                loadFragment(DetailsAdquirenteFragment.newInstance((DataMovimientoAdq) data));
            }
        } else {
            throw new NullObjectExcepcion("Type is not recognized");
        }
    }
}
