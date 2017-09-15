package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.Envios;
import com.pagatodo.yaganaste.data.model.Payments;
import com.pagatodo.yaganaste.data.model.Servicios;
import com.pagatodo.yaganaste.data.model.webservice.response.trans.EjecutarTransaccionResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.ISessionExpired;
import com.pagatodo.yaganaste.interfaces.enums.MovementsTab;
import com.pagatodo.yaganaste.interfaces.enums.TransferType;
import com.pagatodo.yaganaste.ui._controllers.manager.AddFavoritesActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui.payments.fragments.PaymentAuthorizeFragment;
import com.pagatodo.yaganaste.ui.payments.fragments.PaymentSuccessFragment;
import com.pagatodo.yaganaste.ui.payments.managers.PaymentsProcessingManager;
import com.pagatodo.yaganaste.ui.payments.presenters.PaymentsProcessingPresenter;
import com.pagatodo.yaganaste.ui.payments.presenters.interfaces.IPaymentsProcessingPresenter;
import com.pagatodo.yaganaste.utils.Constants;
import com.pagatodo.yaganaste.utils.Recursos;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.yaganaste.interfaces.enums.Direction.FORDWARD;
import static com.pagatodo.yaganaste.utils.Constants.MESSAGE;
import static com.pagatodo.yaganaste.utils.Constants.RESULT;
import static com.pagatodo.yaganaste.utils.Constants.RESULT_CODE_BACK_PRESS;
import static com.pagatodo.yaganaste.utils.Constants.RESULT_CODE_FAIL;

/**
 * Created by Jordan on 25/04/2017.
 */

public class PaymentsProcessingActivity extends LoaderActivity implements PaymentsProcessingManager, ISessionExpired {

    public static final int IDCOMERCIO_YA_GANASTE = 8609;
    @BindView(R.id.container)
    FrameLayout container;

    private View llMain;

    IPaymentsProcessingPresenter presenter;
    Object pago;
    private boolean isAvailableToBack = false;
    private MovementsTab tab;
    private String mensajeLoader = "";
    EjecutarTransaccionResponse response;
    private String nombreComercio = "";
    private int idComercio = 0;
    private int idTipoComercio = 0;
    private String referencia = "";

    public static final String EVENT_SEND_PAYMENT = "EVENT_SEND_PAYMENT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_generic_fragment_container);
        presenter = new PaymentsProcessingPresenter(this);
        pago = getIntent().getExtras().get("pagoItem");
        tab = (MovementsTab) getIntent().getExtras().get("TAB");
        llMain = findViewById(R.id.ll_main);

        initViews();

        if (tab != MovementsTab.TAB3) {
            changeToolbarVisibility(false);
            onEvent(EVENT_SEND_PAYMENT, pago);
        } else {
            hideLoader();
            isAvailableToBack = true;
            llMain.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gradient_bottom));
            loadFragment(PaymentAuthorizeFragment.newInstance((Payments) pago), FORDWARD, true);
        }
    }

    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        switch (event) {
            case EVENT_SEND_PAYMENT:
                try {

                    if (data instanceof Envios) {
                        // if (!nombreComercio.equals(YA_GANASTE)) {
                        int idComercio = ((Envios) data).getComercio().getIdComercio();
                        if (!(idComercio == IDCOMERCIO_YA_GANASTE)) {
                            mensajeLoader = getString(R.string.envio_interbancario, mensajeLoader);
                            showLoader(getString(R.string.procesando_envios_inter_loader));
                        }
                    }
                    showLoader(mensajeLoader);
                    presenter.sendPayment(tab, data);
                } catch (OfflineException e) {
                    e.printStackTrace();
                    onError(getString(R.string.no_internet_access));
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVisibilityPrefer(false);
    }

    @Override
    public boolean requiresTimer() {
        return true;
    }


    private void initViews() {
        ButterKnife.bind(this);
        switch (tab) {
            case TAB1:
                mensajeLoader = getString(R.string.procesando_recarga_loader);
                break;
            case TAB2:
                mensajeLoader = getString(R.string.procesando_servicios_loader);
                break;
            case TAB3:
                mensajeLoader = getString(R.string.procesando_envios_loader);
                break;
        }
    }

    public PaymentsProcessingManager getManager() {
        return this;
    }

    public IPaymentsProcessingPresenter getPresenter() {
        return presenter;
    }


    @Override
    public void onBackPressed() {
        if (isAvailableToBack && !isLoaderShow) {
            Intent intent = new Intent();
            if (getCurrentFragment() instanceof PaymentAuthorizeFragment) {
                setResult(RESULT_CODE_BACK_PRESS, intent);
            } else {
                setResult(RESULT_CODE_FAIL, intent);
            }
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    @Override
    public void onSuccessPaymentRespone(DataSourceResult result) {
        isAvailableToBack = true;
        response = (EjecutarTransaccionResponse) result.getData();
        if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
            hideLoader();
            changeToolbarVisibility(true);
            setVisibilityPrefer(false);
            llMain.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_gradient_bottom));
            loadFragment(PaymentSuccessFragment.newInstance((Payments) pago, response), FORDWARD, true);
        } else {
            onFailPaimentResponse(result);
        }
        saveDataResponse();
    }

    @Override
    public void onFailPaimentResponse(DataSourceResult error) {
        String errorTxt = null;
        try {
            EjecutarTransaccionResponse response = (EjecutarTransaccionResponse) error.getData();
            if (response.getMensaje() != null)
                errorTxt = response.getMensaje();

        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }

        onError(errorTxt);
    }

    private void onError(String message) {
        hideLoader();
        Intent intent = new Intent();
        intent.putExtra(RESULT, Constants.RESULT_ERROR);
        intent.putExtra(MESSAGE, message != null ? message : getString(R.string.error_respuesta));
        setResult(RESULT_CODE_FAIL, intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    private void saveDataResponse() {
        nombreComercio = ((Servicios) pago).getComercio().getNombreComercio();
        idComercio = ((Servicios) pago).getComercio().getIdComercio();
        idTipoComercio = ((Servicios) pago).getComercio().getIdTipoComercio();
        referencia = ((Servicios) pago).getReferencia();
    }

    public void openAddFavoritos(View view) {
        Intent intent = new Intent(this, AddFavoritesActivity.class);
        intent.putExtra("nombreComercio", nombreComercio);
        intent.putExtra("idComercio", idComercio);
        intent.putExtra("idTipoComercio", idTipoComercio);
        intent.putExtra("referencia", referencia);
        startActivity(intent);
    }
}
