package com.pagatodo.yaganaste;

import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.pagatodo.yaganaste.data.local.persistence.Preferencias;
import com.pagatodo.yaganaste.net.RequestHeaders;

/**
 * Created by flima on 17/03/17.
 */

public class App extends MultiDexApplication {
    private static App m_singleton;

    private Preferencias prefs;
    private static Context context;



    @Override
    public void onCreate() {
        super.onCreate();
        m_singleton = this;
        MultiDex.install(this);
        this.prefs = new Preferencias(this);
        context = this;
        RequestHeaders.initHeaders(this);
        // Required initialization logic here!
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
}
