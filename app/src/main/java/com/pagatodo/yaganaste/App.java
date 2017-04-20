package com.pagatodo.yaganaste;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.dspread.xpos.QPOSService;
import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui.adquirente.readers.IposListener;
import com.pagatodo.yaganaste.utils.UI;

/**
 * Created by flima on 17/03/17.
 */

public class App extends MultiDexApplication {
    private static App m_singleton;

    private Preferencias prefs;
    private static Context context;
    public QPOSService pos;
    public IposListener emvListener;

    @Override
    public void onCreate() {
        super.onCreate();
        m_singleton = this;
        //MultiDex.install(this);
        this.prefs = new Preferencias(this);
        System.loadLibrary("a01jni");
        context = this;
        RequestHeaders.initHeaders(this);
        initEMVListener();

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {
                /*if (SingletonSesion.isInSession()) {
                    Log.e("SessionPaused", SingletonSesion.isInSession() + "");
                    if (!SingletonSesion.isFlowUser()) {
                        closeSession();
                        SingletonSesion.setInSession(false);
                        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }*/
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                UI.showToastShort(activity.getClass().getName(),getApplicationContext());

            }
        });
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

    public static App getInstance() {
        return m_singleton;
    }


    public Preferencias getPrefs() {
        return this.prefs;
    }

    public static Context getContext(){
        return context;
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
}
