package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.Recarga;
import com.pagatodo.yaganaste.data.model.Servicios;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.EjecutarTransaccionResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.payments.fragments.PaymentAuthorizeFragment;
import com.pagatodo.yaganaste.ui.payments.fragments.PaymentSuccessFragment;
import com.pagatodo.yaganaste.ui.payments.managers.PaymentsProcessingManager;
import com.pagatodo.yaganaste.ui.payments.presenters.PaymentsProcessingPresenter;
import com.pagatodo.yaganaste.ui.payments.presenters.interfaces.IPaymentsProcessingPresenter;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.interfaces.enums.Direction.*;

/**
 * Created by Jordan on 25/04/2017.
 */

public class PaymentsProcessingActivity extends SupportFragmentActivity implements PaymentsProcessingManager {
    @BindView(R.id.progressGIF)
    ProgressLayout progressLayout;
    @BindView(R.id.container)
    FrameLayout container;

    IPaymentsProcessingPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_generic_fragment_container);
        initViews();
        progressLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        presenter = new PaymentsProcessingPresenter(this);

        Object pago = getIntent().getExtras().get("pagoItem");
        MovementsTab tab = (MovementsTab) getIntent().getExtras().get("TAB");

        try {
            if (pago instanceof Recarga || pago instanceof Servicios) {
                presenter.sendPayment(tab, pago);
            } else if (pago instanceof Envios) {
                hideLoader();
                container.setVisibility(View.VISIBLE);
                View root = container.getRootView();
                root.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gradient_bottom));
                loadFragment(PaymentAuthorizeFragment.newInstance((Envios) pago), NONE, false);
            }

        } catch (OfflineException e) {
            e.printStackTrace();
        }
        //loadFragment(SendPaymentFragment.newInstance(), Direction.NONE, false);
    }

    private void initViews() {
        ButterKnife.bind(this);
        showLoader("Procesando Pago");
    }

    public PaymentsProcessingManager getManager() {
        return this;
    }

    public IPaymentsProcessingPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void showLoader(String message) {
        progressLayout.setTextMessage(message);
        progressLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoader() {
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void showError(Object error) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(2, intent);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onSuccessPaymentRespone(DataSourceResult result) {
        hideLoader();
        container.setVisibility(View.VISIBLE);
        View root = container.getRootView();
        root.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gradient_bottom));
        loadFragment(PaymentSuccessFragment.newInstance(), NONE, false);
    }

    @Override
    public void onFailPaimentResponse(DataSourceResult error) {
        try {
            EjecutarTransaccionResponse data = (EjecutarTransaccionResponse) error.getData();
            if (data.getMensaje() != null)
                Toast.makeText(this, data.getMensaje(), Toast.LENGTH_LONG).show();

        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }

        Intent intent = new Intent();
        intent.putExtra("MESSAGE", "Fail");
        setResult(2, intent);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
