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
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.EjecutarTransaccionResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.ISessionExpired;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.payments.fragments.PaymentSuccessFragment;
import com.pagatodo.yaganaste.ui.payments.managers.PaymentsProcessingManager;
import com.pagatodo.yaganaste.ui.payments.presenters.PaymentsProcessingPresenter;
import com.pagatodo.yaganaste.ui.payments.presenters.interfaces.IPaymentsProcessingPresenter;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.Recursos;
import com.pagatodo.yaganaste.utils.customviews.ProgressLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.interfaces.enums.Direction.NONE;
import static com.pagatodo.yaganaste.utils.Constants.MESSAGE;
import static com.pagatodo.yaganaste.utils.Constants.RESULT;

/**
 * Created by Jordan on 25/04/2017.
 */

public class PaymentsProcessingActivity extends SupportFragmentActivity implements PaymentsProcessingManager, ISessionExpired {
    @BindView(R.id.progressGIF)
    ProgressLayout progressLayout;
    @BindView(R.id.container)
    FrameLayout container;

    IPaymentsProcessingPresenter presenter;
    Object pago;
    private boolean isAvailableToBack = false;

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

        pago = getIntent().getExtras().get("pagoItem");
        MovementsTab tab = (MovementsTab) getIntent().getExtras().get("TAB");

        try {
            presenter.sendPayment(tab, pago);
        } catch (OfflineException e) {
            e.printStackTrace();
            onError(getString(R.string.no_internet_access));
        }
        /*if (pago instanceof Recarga || pago instanceof Servicios) {
            try {
                presenter.sendPayment(tab, pago);
            } catch (OfflineException e) {
                e.printStackTrace();
                onError(getString(R.string.no_internet_access));
            }
        } else if (pago instanceof Envios) {
            hideLoader();
            container.setVisibility(View.VISIBLE);
            View root = container.getRootView();
            root.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gradient_bottom));
            loadFragment(PaymentAuthorizeFragment.newInstance((Envios) pago), NONE, false);
        }*/
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
        if (isAvailableToBack) {
            Intent intent = new Intent();
            setResult(2, intent);
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    @Override
    public void onSuccessPaymentRespone(DataSourceResult result) {
        isAvailableToBack = true;
        EjecutarTransaccionResponse response = (EjecutarTransaccionResponse) result.getData();
        if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
            hideLoader();
            container.setVisibility(View.VISIBLE);
            View root = container.getRootView();
            root.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gradient_bottom));
            loadFragment(PaymentSuccessFragment.newInstance((Payments) pago, response), NONE, false);
//        } else if (response.getCodigoRespuesta() == Recursos.CODE_SESSION_EXPIRED) {
//            hideLoader();
//            errorSessionExpired(result);
        } else {
            onFailPaimentResponse(result);
        }
    }

    @Override
    public void onFailPaimentResponse(DataSourceResult error) {
        String errorTxt = null;
        try {
            EjecutarTransaccionResponse response = (EjecutarTransaccionResponse) error.getData();
            if (response.getMensaje() != null)
                errorTxt = response.getMensaje();
            //Toast.makeText(this, response.getMensaje(), Toast.LENGTH_LONG).show();

        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }

        onError(errorTxt);
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void onError(String message) {
        Intent intent = new Intent();
        intent.putExtra(RESULT, Constants.RESULT_ERROR);
        intent.putExtra(MESSAGE, message != null ? message : getString(R.string.error_respuesta));
        setResult(2, intent);
        finish();
    }
}
