package com.pagatodo.yaganaste.ui_wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui_wallet.adapters.RecyclerGenericBase;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ISearchCarrier;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.ui_wallet.PaymentActivity.PAYMENT_DATA;
import static com.pagatodo.yaganaste.ui_wallet.PaymentActivity.PAYMENT_IS_FAV;
import static com.pagatodo.yaganaste.ui_wallet.adapters.RecyclerGenericBase.VERTICAL_ORIENTATION;

public class SearchCarrierActivity extends LoaderActivity implements ISearchCarrier {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.activity_search_edittext)
    EditText searchEditText;

    public static final String SEARCH_DATA = "SEARCH_DATA";
    public static final String SEARCH_IS_RELOAD = "SEARCH_IS_RELOAD";
    private ArrayList<ComercioResponse> comercioResponse;
    private ArrayList<DataFavoritos> dataFavoritos;
    private boolean isReload;
    private RecyclerGenericBase recyclerGenericBase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_carrier);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getExtras() != null) {
            isReload = getIntent().getBooleanExtra(SEARCH_IS_RELOAD, true);
            if (isReload) {
                comercioResponse = (ArrayList<ComercioResponse>) getIntent().getExtras().get(SEARCH_DATA);
            } else {
                comercioResponse = (ArrayList<ComercioResponse>) getIntent().getExtras().get(SEARCH_DATA);
            }
        }
        initViews();
    }

    private void initViews() {
        ButterKnife.bind(this);

        recyclerGenericBase = new RecyclerGenericBase(recyclerView, VERTICAL_ORIENTATION);
        recyclerGenericBase.createRecyclerList(this, comercioResponse, searchEditText);


       // searchEditText.addTextChangedListener(filterTextWatcher);
    }

    @Override
    public boolean requiresTimer() {
        return false;
    }

    @Override
    public void setData(ComercioResponse posCcomercioResponse) {
      //  Toast.makeText(this, "Pos " + posCcomercioResponse, Toast.LENGTH_SHORT).show();
        Intent intentPayment = new Intent(App.getContext(), PaymentActivity.class);
        intentPayment.putExtra(PAYMENT_DATA, posCcomercioResponse);
        intentPayment.putExtra(PAYMENT_IS_FAV, false);
        startActivity(intentPayment);
        finish();
    }

    @Override
    protected void onStop() {
     //   searchEditText.removeTextChangedListener(filterTextWatcher);
        super.onStop();
    }
}
