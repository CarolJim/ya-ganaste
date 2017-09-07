package com.pagatodo.yaganaste;

import android.app.Activity;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.widget.Toast;

import com.dspread.xpos.QPOSService;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.data.model.SingletonSession;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.net.VolleySingleton;
import com.pagatodo.yaganaste.ui._controllers.MainActivity;
import com.pagatodo.yaganaste.ui.adquirente.readers.IposListener;
import com.pagatodo.yaganaste.utils.ApplicationLifecycleHandler;
import com.pagatodo.yaganaste.utils.NotificationBuilder;
import com.pagatodo.yaganaste.utils.ScreenReceiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;

/**
 * Created by flima on 17/03/17.
 */

public class App extends Application {
    private static App m_singleton;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public QPOSService pos;
    public IposListener emvListener;
    private Preferencias prefs;

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
        //MultiDex.install(this);

        this.prefs = new Preferencias(this);
        System.loadLibrary("a01jni");
        initEMVListener();
        RequestHeaders.initHeaders(this);

        lifecycleHandler = new ApplicationLifecycleHandler();
        registerActivityLifecycleCallbacks(lifecycleHandler);
        registerComponentCallbacks(lifecycleHandler);

        new ScreenReceiver(getContext(), lifecycleHandler);
        quoeeuActivites = new HashMap<>();
    }

    public void addToQuee(Activity activity) {
        quoeeuActivites.put(activity.getClass().getSimpleName(), activity);
    }

    public void removeFromQuee(Activity activity) {
        quoeeuActivites.remove(activity.getClass().getSimpleName());
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    @Override
    public void onLowMemory() {
        super.onLowMemory();
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
        Log.i("=====", "===========>>>");
        Log.i("=====", "===========>>> initEMVListener");
    }


    public Preferencias getPrefs() {
        return this.prefs;
    }

    public void cerrarApp() {
        VolleySingleton.getInstance(App.getContext()).deleteQueue();

        try {
            ApiAdtvo.cerrarSesion();// Se envia null ya que el Body no aplica.
        } catch (OfflineException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(SELECTION, MAIN_SCREEN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);


        if (lifecycleHandler.isInBackground()) {
            NotificationBuilder.createCloseSessionNotification(this, intent, "Titulo", getString(R.string.close_sesion_body));
            List<Activity> toClose = new ArrayList<>();
            for (Map.Entry<String, Activity> current : quoeeuActivites.entrySet()) {
                toClose.add(current.getValue());
            }
            for (Activity current : toClose) {
                current.finish();
            }
        } else {
            startActivity(intent);
        }

    }




}
