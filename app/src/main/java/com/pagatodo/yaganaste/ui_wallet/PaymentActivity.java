package com.pagatodo.yaganaste.ui_wallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui_wallet.fragments.PaymentFormFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentActivity extends LoaderActivity implements View.OnClickListener {

    public static final String PAYMENT_DATA = "PAYMENT_DATA";
    public static final String PAYMENT_IS_FAV = "PAYMENT_IS_FAV";
    @BindView(R.id.btn_back)
    AppCompatImageView back;

    private ComercioResponse comercioResponse;
    private DataFavoritos dataFavoritos;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        if (getIntent().getExtras() != null) {
            isFavorite = getIntent().getBooleanExtra(PAYMENT_IS_FAV, false);
            if (!isFavorite) {
                comercioResponse = (ComercioResponse) getIntent().getExtras().get(PAYMENT_DATA);
            } else {
                dataFavoritos = (DataFavoritos) getIntent().getExtras().get(PAYMENT_DATA);
            }
        }
        initViews();
    }

    private void initViews() {
        ButterKnife.bind(this);
        back.setOnClickListener(this);
        if (!isFavorite) {
            loadFragment(PaymentFormFragment.newInstance(comercioResponse), R.id.fragment_container);
        } else {
            loadFragment(PaymentFormFragment.newInstance(dataFavoritos), R.id.fragment_container);
        }
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PaymentFormFragment myFragment = (PaymentFormFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        myFragment.onActivityResult(requestCode, resultCode, data);
    }
}
