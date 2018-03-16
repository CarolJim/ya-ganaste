package com.pagatodo.yaganaste.ui._controllers;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.room_db.AppDatabase;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ObtenerCatalogoRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerCatalogosResponse;
import com.pagatodo.yaganaste.data.room_db.DatabaseManager;
import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.MontoComercio;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.ui._controllers.manager.LoaderActivity;
import com.pagatodo.yaganaste.utils.FileDownloadListener;
import com.pagatodo.yaganaste.utils.StringConstants;
import com.pagatodo.yaganaste.utils.ValidatePermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.NO_SIM_CARD;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_OK;
import static com.pagatodo.yaganaste.utils.Recursos.DEBUG;

public class SplashActivity extends LoaderActivity implements IRequestResult, FileDownloadListener {
    private Preferencias preferencias;
    private AppDatabase db;
    private static final String TAG = "SplashActivity";
    boolean downloadFile = false;
    ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_activity_layout);
        imgLogo = (ImageView) findViewById(R.id.img_logo_splash);
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
        }

        if (!DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
        preferencias = App.getInstance().getPrefs();
        db = App.getAppDatabase();
        final IRequestResult iRequestResult = this;
        final Handler handler = new Handler();
        preferencias = App.getInstance().getPrefs();
        new DatabaseManager().checkCountries();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    ObtenerCatalogoRequest request = new ObtenerCatalogoRequest();
                    request.setVersion(preferencias.loadData(StringConstants.CATALOG_VERSION).isEmpty() ? "1" : preferencias.loadData(StringConstants.CATALOG_VERSION));
                    ApiAdtvo.obtenerCatalogos(request, iRequestResult);
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
                    preferencias.saveData(StringConstants.CATALOG_VERSION, response.getData().getVersion());
                    List<MontoComercio> montos = new ArrayList<>();
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

    private void callNextActivity() {
        Intent intent = null;
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, imgLogo, "transition");
        int revealX = (int) (imgLogo.getX() + (imgLogo.getWidth() - 1) / 2);
        int revealY = (int) (imgLogo.getY() + (imgLogo.getHeight() - 1) / 2);

        /*TODO Descomentar para validar flujo correctamente*/
        if (ValidatePermissions.validateSIMCard(this)) {
            /*if(!RequestHeaders.getTokenauth().isEmpty()) {
                intent = new Intent(SplashActivity.this, AccountActivity.class);
                intent.putExtra(SELECTION,GO_TO_LOGIN);
            }else {*/
            intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.putExtra(SELECTION, MAIN_SCREEN);
            intent.putExtra(MainActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
            intent.putExtra(MainActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);
            //}
        } else {
            intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.putExtra(SELECTION, NO_SIM_CARD);
            intent.putExtra(MainActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
            intent.putExtra(MainActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);
        }

        /**
         * Iniciamos el flujo normal solo si nuestra bandera de downloadFile es falsa, esto signoica
         * que no estamos descargand onada, en caso contrario en automatico se hace el proceso para
         * descargar y abrir por el hilo de notificacion
         */
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
}
