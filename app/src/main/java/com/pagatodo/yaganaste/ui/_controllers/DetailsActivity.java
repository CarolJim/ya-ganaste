package com.pagatodo.yaganaste.ui._controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.TransactionAdqData;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.DataMovimientoAdq;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosResponse;
import com.pagatodo.yaganaste.exceptions.IllegalCallException;
import com.pagatodo.yaganaste.exceptions.NullObjectExcepcion;
import com.pagatodo.yaganaste.interfaces.OnEventListener;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.adqtransactioncancel.fragments.DetailTransactionAdqCancel;
import com.pagatodo.yaganaste.ui.adquirente.InsertDongleFragment;
import com.pagatodo.yaganaste.ui.adquirente.TransactionResultFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.DetailsAdquirenteFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.DetailsEmisorFragment;

import java.io.Serializable;

import static com.pagatodo.yaganaste.ui._controllers.AdqActivity.EVENT_GO_TRANSACTION_RESULT;
import static com.pagatodo.yaganaste.ui.maintabs.fragments.PaymentsFragment.RESULT_CANCEL_OK;

/**
 * @author Juan Guerra on 11/04/2017.
 */

public class DetailsActivity extends LoaderActivity implements OnEventListener {

    public static final String DATA = "data";
    public static final String TYPE = "type";
    public final static String EVENT_GO_TO_FINALIZE_SUCCESS = "FINALIZAR_CANCELACION_SUCCESS";
    public final static String EVENT_GO_TO_FINALIZE_ERROR = "FINALIZAR_CANCELACION_ERROR";

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
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        switch (event) {
            case EVENT_GO_TRANSACTION_RESULT:
                loadFragment(TransactionResultFragment.newInstance(TransactionAdqData.getCurrentTransaction().getPageResult()), Direction.FORDWARD, true);
                break;
            case EVENT_GO_TO_FINALIZE_SUCCESS:
                setResult(RESULT_CANCEL_OK);
                this.finish();
                break;

            case EVENT_GO_TO_FINALIZE_ERROR:
                setResult(-1);
                this.finish();
                break;
        }

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

    public void loadCancelFragment(DataMovimientoAdq data) {
        loadFragment(DetailTransactionAdqCancel.newInstance(data), Direction.FORDWARD, true);
    }

    public void loadInsertDongleFragment(DataMovimientoAdq dataMovimientoAdq) {
        loadFragment(InsertDongleFragment.newInstance(true, dataMovimientoAdq), Direction.FORDWARD, true);
    }

    @Override
    public void onBackPressed() {
        if (!isLoaderShow) {
            super.onBackPressed();
        }
    }

    enum TYPES {
        EMISOR,
        ADQUIRENTE
    }
}
