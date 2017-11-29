package com.pagatodo.yaganaste;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.dspread.xpos.QPOSService;
import com.facebook.stetho.Stetho;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.net.VolleySingleton;
import com.pagatodo.yaganaste.ui._controllers.MainActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.adquirente.readers.IposListener;
import com.pagatodo.yaganaste.utils.ApplicationLifecycleHandler;
import com.pagatodo.yaganaste.utils.NotificationBuilder;
import com.pagatodo.yaganaste.utils.ScreenReceiver;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.IS_FROM_TIMER;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.utils.Recursos.CONSULT_FAVORITE;
import static com.pagatodo.yaganaste.utils.Recursos.DISCONNECT_TIMEOUT;

/**
 * Created by flima on 17/03/17.
 */

public class App extends Application {

    private static App m_singleton;
    private CountDownTimer countDownTimer;
    private SupportFragmentActivity currentActivity;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public QPOSService pos;
    public IposListener emvListener;
    private Preferencias prefs;
    //variable de status de cuenta
    private String statusId;

    private String datoHuellaC;

    private ApplicationLifecycleHandler lifecycleHandler;

    private Map<String, Activity> quoeeuActivites;

    public static App getInstance() {
        return m_singleton;
    }

    public static Context getContext() {
        return m_singleton.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        String languageToLoad = "es"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        m_singleton = this;
        MultiDex.install(this);
        //Stetho.initializeWithDefaults(this);

        this.prefs = new Preferencias(this);
        System.loadLibrary("a01jni");
        initEMVListener();
        RequestHeaders.initHeaders(this);

        lifecycleHandler = new ApplicationLifecycleHandler();
        registerActivityLifecycleCallbacks(lifecycleHandler);
        registerComponentCallbacks(lifecycleHandler);

        new ScreenReceiver(getContext(), lifecycleHandler);
        quoeeuActivites = new LinkedHashMap<>();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        // Se crea la carpeta donde almacenar los Screenshot para compartir.
        File f = new File(Environment.getExternalStorageDirectory()+getString(R.string.path_image));
        if(!f.exists()){
            f.mkdir();
        }
        //
        statusId = "0";
        datoHuellaC="";
    }

    //Get & Set Status


    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    //Get & Set huella


    public String getCadenaHuella() {
        return datoHuellaC;
    }

    public void setCadenaHuella(String datoHuellaCd) {
        this.datoHuellaC = datoHuellaCd;
    }


    public void addToQuee(Activity activity) {
        quoeeuActivites.put(activity.getClass().getSimpleName(), activity);
        if (activity instanceof SupportFragmentActivity) {
            currentActivity = (SupportFragmentActivity) activity;
        }
    }

    public void removeFromQuee(Activity activity) {
        quoeeuActivites.remove(activity.getClass().getSimpleName());

        Set<Map.Entry<String, Activity>> mapValues = quoeeuActivites.entrySet();
        int maplength = mapValues.size();

        final Map.Entry<String, Activity>[] test = new Map.Entry[maplength];
        mapValues.toArray(test);

        if (test.length > 0 && test[maplength - 1].getValue() instanceof SupportFragmentActivity) {
            this.currentActivity = (SupportFragmentActivity) test[maplength - 1].getValue();
        }

    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    //Inicializa Lector Ipos
    public void initEMVListener() {
        emvListener = new IposListener(getApplicationContext());
        pos = QPOSService.getInstance(QPOSService.CommunicationMode.AUDIO);
        pos.setConext(getApplicationContext());
        pos.setVolumeFlag(false);
        Handler handler = new Handler(Looper.myLooper());
        pos.initListener(handler, emvListener);
    }


    public Preferencias getPrefs() {
        return this.prefs;
    }


    public void cerrarAppsms() {
        VolleySingleton.getInstance(App.getContext()).deleteQueue();

        try {
            ApiAdtvo.cerrarSesion();// Se envia null ya que el Body no aplica.
        } catch (OfflineException e) {
            e.printStackTrace();
        }
    }
    public void cerrarApp() {
        VolleySingleton.getInstance(App.getContext()).deleteQueue();
        prefs.saveDataBool(CONSULT_FAVORITE, false);

        try {
            ApiAdtvo.cerrarSesion();// Se envia null ya que el Body no aplica.
        } catch (OfflineException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(SELECTION, MAIN_SCREEN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        Log.e("APP", "Close From: " + Thread.currentThread().getStackTrace()[1].getMethodName());

        if (lifecycleHandler.isInBackground()) {
            NotificationBuilder.createCloseSessionNotification(this, intent, getString(R.string.app_name), getString(R.string.close_sesion_bodynuevo));
            List<Activity> toClose = new ArrayList<>();
            for (Map.Entry<String, Activity> current : quoeeuActivites.entrySet()) {
                toClose.add(current.getValue());
            }
            for (Activity current : toClose) {
                current.finish();
            }
        } else {
            intent.putExtra(IS_FROM_TIMER, true);
            startActivity(intent);
        }

    }

    public void resetTimer(SupportFragmentActivity from) {
        stopTimer();
        if (from.requiresTimer()) {
            startCounter();
        }
    }

    public void resetTimer() {
        stopTimer();
        if (currentActivity != null && currentActivity.requiresTimer()) {
            startCounter();
        }
    }

    public void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void startCounter() {
        countDownTimer = new CountDownTimer(DISCONNECT_TIMEOUT, DISCONNECT_TIMEOUT) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                cerrarApp();
            }
        };
        countDownTimer.start();
    }
}
