package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

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

import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;

public class SplashActivity extends AppCompatActivity implements IRequestResult {
    private Preferencias pref;
    private CatalogsDbApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_activity_layout);
        api = new CatalogsDbApi(this);
        pref = App.getInstance().getPrefs();
        final IRequestResult iRequestResult = this;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (api.isCatalogTableEmpty()) {
                        ApiAdtvo.obtenerCatalogos(new ObtenerCatalogoRequest(), iRequestResult);
                    }else {
                        callMainActivity();
                    }
                } catch (OfflineException e) {
                    e.printStackTrace();
                    callMainActivity();
                }
            }
        }, 2000);
    }

    @Override
    public void onSuccess(DataSourceResult result) {
        switch (result.getWebService()) {
            case OBTENER_CATALOGOS:
                ObtenerCatalogosResponse response = (ObtenerCatalogosResponse) result.getData();
                if (response.getCodigoRespuesta() == CODE_OK) {
                    try {
                        api.insertComercios(response.getData().getComercios());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                callMainActivity();
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        callMainActivity();
    }

    private void callMainActivity() {
        Class clazz = MainActivity.class;
        Intent intent = new Intent(SplashActivity.this, clazz);
        startActivity(intent);
        SplashActivity.this.finish();
    }
}
