package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.crashlytics.android.Crashlytics;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerCatalogoRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerCatalogosResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.utils.StringConstants;
import com.pagatodo.yaganaste.utils.ValidatePermissions;

import io.fabric.sdk.android.Fabric;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.NO_SIM_CARD;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

public class SplashActivity extends SupportFragmentActivity implements IRequestResult {
    private Preferencias pref;
    private CatalogsDbApi api;
    private Preferencias preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_activity_layout);
        api = new CatalogsDbApi(this);
        pref = App.getInstance().getPrefs();
        final IRequestResult iRequestResult = this;
        final Handler handler = new Handler();
        preferencias = App.getInstance().getPrefs();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    //if (api.isCatalogTableEmpty()) {
                    ObtenerCatalogoRequest request = new ObtenerCatalogoRequest();
                    request.setVersion(preferencias.loadData(StringConstants.CATALOG_VERSION).isEmpty() ? "1" : preferencias.loadData(StringConstants.CATALOG_VERSION));
                    ApiAdtvo.obtenerCatalogos(request, iRequestResult);
                    //} else {
                    //    callNextActivity();
                    //}
                } catch (OfflineException e) {
                    e.printStackTrace();
                    callNextActivity();
                }
            }
        }, 2000);
    }

    @Override
    public void onSuccess(DataSourceResult result) {
        switch (result.getWebService()) {
            case OBTENER_CATALOGOS:
                ObtenerCatalogosResponse response = (ObtenerCatalogosResponse) result.getData();
                if (response.getCodigoRespuesta() == CODE_OK && response.getData() != null) {
                    try {
                        preferencias.saveData(StringConstants.CATALOG_VERSION, response.getData().getVersion());
                        api.insertComercios(response.getData().getComercios());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                callNextActivity();
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        callNextActivity();
    }

    private void callNextActivity() {
        Intent intent = null;
        /*TODO Flujo para Evitar validacions de Cuenta y Dispositivo*/
        //intent = new Intent(SplashActivity.this, MainActivity.class);
        //intent.putExtra(SELECTION, MAIN_SCREEN);

        /*TODO Descomentar para validar flujo correctamente*/
        if (ValidatePermissions.validateSIMCard(this)) {
            /*if(!RequestHeaders.getTokenauth().isEmpty()) {
                intent = new Intent(SplashActivity.this, AccountActivity.class);
                intent.putExtra(SELECTION,GO_TO_LOGIN);
            }else {*/
            intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.putExtra(SELECTION, MAIN_SCREEN);
            //}
        } else {
            intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.putExtra(SELECTION, NO_SIM_CARD);
        }

        startActivity(intent);
        SplashActivity.this.finish();
    }
}
