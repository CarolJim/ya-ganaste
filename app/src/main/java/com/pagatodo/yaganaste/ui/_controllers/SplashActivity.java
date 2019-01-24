package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.BuildConfig;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerCatalogoRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerCatalogosResponse;
import com.pagatodo.yaganaste.data.room_db.AppDatabase;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.modules.onboarding.OnboardingActivity;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IGetInfoFromFirebase;
import com.pagatodo.yaganaste.utils.FileDownloadListener;
import com.pagatodo.yaganaste.utils.ForcedUpdateChecker;
import com.pagatodo.yaganaste.utils.Utils;
import com.pagatodo.yaganaste.utils.ValidatePermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.NO_SIM_CARD;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.utils.Recursos.CATALOG_VERSION;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.CONNECTION_TIMEOUT;
import static com.pagatodo.yaganaste.utils.Recursos.CONNECTION_TYPE;
import static com.pagatodo.yaganaste.utils.Recursos.EMAIL_REGISTER;
import static com.pagatodo.yaganaste.utils.Recursos.EVENT_REGISTER_YG;
import static com.pagatodo.yaganaste.utils.Recursos.EVENT_SPLASH;
import static com.pagatodo.yaganaste.utils.Recursos.HAS_SESSION;
import static com.pagatodo.yaganaste.utils.Recursos.IS_OPERADOR;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOGS_PROD;
import static com.pagatodo.yaganaste.utils.Recursos.USER_PROVISIONED;

public class SplashActivity extends LoaderActivity implements IRequestResult, FileDownloadListener,
        IGetInfoFromFirebase {
    private Preferencias preferencias;
    private AppDatabase db;
    private static final String TAG = "SplashActivity";
    boolean downloadFile = false;
    ImageView imgLogo;
    private Preferencias prefs = App.getInstance().getPrefs();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_activity_layout);
        imgLogo = (ImageView) findViewById(R.id.img_logo_splash);


        App.getDatabaseReference().child("Ya-Ganaste-5_0/STTNGS/ConnectionTimeout").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int connectionTimeout = dataSnapshot.getValue(Integer.class);
                    App.getInstance().getPrefs().saveDataInt(CONNECTION_TIMEOUT, connectionTimeout);
                } else {
                    App.getInstance().getPrefs().saveDataInt(CONNECTION_TIMEOUT, 25000);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Validar visualización de Logs
        App.getDatabaseReference().child("Ya-Ganaste-5_0/STTNGS/ShowLogs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    boolean showLogs = dataSnapshot.getValue(Boolean.class);
                    App.getInstance().getPrefs().saveDataBool(SHOW_LOGS_PROD, !BuildConfig.DEBUG ? showLogs : true);
                } else {
                    App.getInstance().getPrefs().saveDataBool(SHOW_LOGS_PROD, true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        /*Bundle intent = getIntent().getExtras();
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
                        App.getInstance().downloadFile(urlData,
                                nameData, typeData, this);
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
        }*/

        preferencias = App.getInstance().getPrefs();
        db = App.getAppDatabase();
        preferencias = App.getInstance().getPrefs();
        // new DatabaseManager().checkCountries();
        new ForcedUpdateChecker(this).getUrls(this);



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
                    preferencias.saveData(CATALOG_VERSION, response.getData().getVersion());
                    new DatabaseManager().insertComercios(response.getData().getComercios());
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

    //if ((prefs.containsData(IS_OPERADOR)) || (prefs.containsData(HAS_SESSION) && !RequestHeaders.getTokenauth().isEmpty())) {
    private void callNextActivity() {
        Intent intent = null;
        /*TODO Descomentar para validar flujo correctamente*/
        if (ValidatePermissions.validateSIMCard(this)) {
            /*if(!RequestHeaders.getTokenauth().isEmpty()) {
                intent = new Intent(SplashActivity.this, AccountActivity.class);
                intent.putExtra(SELECTION,GO_TO_LOGIN);
            }else {*/
            /*intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.putExtra(SELECTION, MAIN_SCREEN);*/
            intent = new Intent(SplashActivity.this, OnboardingActivity.class);
            //intent.putExtra(SELECTION, MAIN_SCREEN);
            //}
        } else {
            intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.putExtra(SELECTION, NO_SIM_CARD);
        }

        /**
         * Iniciamos el flujo normal solo si nuestra bandera de downloadFile es falsa, esto significa
         * que no estamos descargando nada, en caso contrario en automatico se hace el proceso para
         * descargar y abrir por el hilo de notificacion
         */
        Bundle bundle = new Bundle();
        bundle.putString(CONNECTION_TYPE, Utils.getTypeConnection());
        FirebaseAnalytics.getInstance(this).logEvent(EVENT_SPLASH, bundle);
        JSONObject props = new JSONObject();
        if (!BuildConfig.DEBUG) {
            try {
                props.put(CONNECTION_TYPE, Utils.getTypeConnection());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            App.mixpanel.track(EVENT_SPLASH, props);
        }
        if (!downloadFile) {
            startActivity(intent/*, SPLASH_ACTIVITY_RESULT, options.toBundle()*/);
            SplashActivity.this.finish();
        }

    }

    @Override
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

    @Override
    public void onUrlsDownload() {
        new ForcedUpdateChecker(this).getPins(this);
    }

    @Override
    public void onPinsDownload() {
        final IRequestResult iRequestResult = this;
        try {
            ObtenerCatalogoRequest request = new ObtenerCatalogoRequest();
            request.setVersion(preferencias.loadData(CATALOG_VERSION).isEmpty() ? "1" : preferencias.loadData(CATALOG_VERSION));
            ApiAdtvo.obtenerCatalogos(request, iRequestResult);
        } catch (OfflineException e) {
            e.printStackTrace();
            callNextActivity();
        }
    }

    @Override
    public void onError() {
        finish();
    }
}
