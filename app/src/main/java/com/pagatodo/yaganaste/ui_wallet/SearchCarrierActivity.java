package com.pagatodo.yaganaste.ui_wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui_wallet.adapters.RecyclerGenericBase;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ISearchCarrier;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui._controllers.PaymentsProcessingActivity.CURRENT_TAB_ID;
import static com.pagatodo.yaganaste.ui_wallet.PaymentActivity.PAYMENT_DATA;
import static com.pagatodo.yaganaste.ui_wallet.PaymentActivity.PAYMENT_IS_FAV;
import static com.pagatodo.yaganaste.ui_wallet.adapters.RecyclerGenericBase.VERTICAL_ORIENTATION;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_RECARGAS;
import static com.pagatodo.yaganaste.utils.Constants.PAYMENT_SERVICIOS;

/**
 * Ecargada de mostrar un RV con el RecyclerGenericBase, ademas de un EditText para realizar un filtro
 * de resultados en la busqueda
 */

public class SearchCarrierActivity extends LoaderActivity implements ISearchCarrier {

    @BindView(R.id.txt_title_search)
    TextView txtTitleSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.activity_search_edittext)
    EditText searchEditText;

    public static final String SEARCH_DATA = "SEARCH_DATA";
    public static final String SEARCH_IS_RELOAD = "SEARCH_IS_RELOAD";
    private ArrayList<Comercio> comercioResponse;
    private boolean isReload;
    private RecyclerGenericBase recyclerGenericBase;
    private int currentTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_carrier);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getExtras() != null) {
            isReload = getIntent().getBooleanExtra(SEARCH_IS_RELOAD, true);
            currentTab = getIntent().getExtras().getInt(CURRENT_TAB_ID);
            if (isReload) {
                comercioResponse = (ArrayList<Comercio>) getIntent().getExtras().get(SEARCH_DATA);
            } else {
                comercioResponse = (ArrayList<Comercio>) getIntent().getExtras().get(SEARCH_DATA);
            }
        }
        initViews();
    }

    private void initViews() {
        ButterKnife.bind(this);
        if (currentTab == PAYMENT_RECARGAS) {
            txtTitleSearch.setText(getString(R.string.btn_recharge_txt));
        } else if (currentTab == PAYMENT_SERVICIOS) {
            txtTitleSearch.setText(getString(R.string.btn_payment_txt));
        }
        /**
         * Creamos el RV con los elementos adicionales del EditText
         */
        recyclerGenericBase = new RecyclerGenericBase(recyclerView, VERTICAL_ORIENTATION);
        recyclerGenericBase.createRecyclerList(this, comercioResponse, searchEditText);

    }

    @Override
    public boolean requiresTimer() {
        return false;
    }

    @Override
    public void setData(Comercio posCcomercioResponse) {
        /**
         * Enviamos el objeto resultante a nuestra actividad de pagos, con un bandera para eliminar
         * la actividad SearchCarrier del BackStack
         */
        Intent intentPayment = new Intent(App.getContext(), PaymentActivity.class);
        intentPayment.putExtra(PAYMENT_DATA, posCcomercioResponse);
        intentPayment.putExtra(PAYMENT_IS_FAV, false);
        intentPayment.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentPayment);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
