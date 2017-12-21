package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.local.persistence.db.CatalogsDbApi;
import com.pagatodo.yaganaste.data.model.db.Countries;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerCatalogoRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerCatalogosResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.utils.JsonManager;
import com.pagatodo.yaganaste.utils.StringConstants;
import com.pagatodo.yaganaste.utils.ValidatePermissions;
import com.pagatodo.yaganaste.utils.customviews.FileDownload;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

import io.fabric.sdk.android.Fabric;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.NO_SIM_CARD;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.DEBUG;

public class SplashActivity extends LoaderActivity implements IRequestResult {
    private Preferencias pref;
    private CatalogsDbApi api;
    private Preferencias preferencias;
    private static final String TAG = "SplashActivity";
    boolean downloadFile = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_activity_layout);

        // Codigo para mostrar el token y los datos de las notificaciones FCM. Solo necesitamos
        // acomodar el Data con valores validos
        Log.d(TAG, "Token ID: " + FirebaseInstanceId.getInstance().getToken());
        Bundle intent = getIntent().getExtras();
        if (intent != null && intent.get("id") != null) {
            // Recibimos todos los datos que estan en nuestro Intent
            String idType = getIntent().getExtras().get("id").toString();
            String urlData = getIntent().getExtras().get("urlData").toString();
            String nameData = getIntent().getExtras().get("nameData").toString();
            String typeData = getIntent().getExtras().get("typeData").toString();

            if (idType != null) {
                Log.d(TAG, "idType: " + idType);
                switch (idType) {
                    case "1":
                        //String url = "https://play.google.com/store/apps/details?id=com.pagatodo.yaganaste";
                        String url = urlData;
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                        break;
                    case "2":
                        FileDownload fileDownload = new FileDownload(this, urlData,
                                nameData, typeData);
                        fileDownload.execute("");
                        downloadFile = true;
                        showLoader(getString(R.string.download_file));
                        break;
                    case "3":
                        //  startActivity(new Intent(this, TestActivity.class));
                        // this.finish();
                        break;
                    case "4":
                        break;
                }
            }
        }

        if (!DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
        api = new CatalogsDbApi(this);
        pref = App.getInstance().getPrefs();
        final IRequestResult iRequestResult = this;
        final Handler handler = new Handler();
        preferencias = App.getInstance().getPrefs();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (api.isPaisesTableEmpty()) {
                        String json = JsonManager.loadJSONFromAsset("files/paises.json");
                        Type token = new TypeToken<List<Countries>>() {
                        }.getType();
                        List<Countries> countries = new Gson().fromJson(json, token);
                        api.insertPaises(countries);
                    }

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
    public boolean requiresTimer() {
        return false;
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

        /**
         * Iniciamos el flujo normal solo si nuestra bandera de downloadFile es falsa, esto signoica
         * que no estamos descargand onada, en caso contrario en automatico se hace el proceso para
         * descargar y abrir por el hilo de notificacion
         */
        if (!downloadFile) {
            startActivity(intent);
            SplashActivity.this.finish();
        }

    }

    /**
     * Recibimos la URI del archivo que descargamos asi como su tipo de data. Esto los sirve para
     * hacer dierencia entre su tipo de MIME
     *
     * @param uriPath
     * @param typeData
     */
    public void returnUri(Uri uriPath, String typeData) {
        /*
         - Creamos una nueva instancia del archivo por medio de su URI. Esto SOLO lo localiza
         - Hacemos SET de la accion de ACTION_VIEW para abrir el archivo
          */

        File af = new File(String.valueOf(uriPath));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);

        String typeMime = "";
        switch (typeData) {
            case "1":
                typeMime = "text/*";
                break;
            case "2":
                typeMime = "image/*";
                break;
            case "3":
                typeMime = "video/*";
                break;

        }

        /**
         * Hacemos SET de la orden para abrir el File por medio de Uri.fromFile(af), si intentamos
         * abrir directo el URI no funciona, necesitamos ambas combinaciones
         */
        intent.setDataAndType(Uri.fromFile(af), typeMime);
        startActivity(intent);
        hideLoader();
        this.finish();
    }
}
