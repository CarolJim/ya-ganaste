package com.pagatodo.yaganaste;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.dspread.xpos.QPOSService;
import com.facebook.stetho.Stetho;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.pagatodo.yaganaste.data.Preferencias;
import com.pagatodo.yaganaste.data.room_db.AppDatabase;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.net.VolleySingleton;
import com.pagatodo.yaganaste.ui._controllers.MainActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.adquirente.readers.IposListener;
import com.pagatodo.yaganaste.utils.ApplicationLifecycleHandler;
import com.pagatodo.yaganaste.utils.FileDownload;
import com.pagatodo.yaganaste.utils.FileDownloadListener;
import com.pagatodo.yaganaste.utils.NotificationBuilder;
import com.pagatodo.yaganaste.utils.ScreenReceiver;
import com.pagatodo.yaganaste.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import io.fabric.sdk.android.Fabric;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.IS_FROM_TIMER;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.utils.Recursos.SESSION_TIMEOUT;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOGS_PROD;
import static com.pagatodo.yaganaste.utils.Recursos.TOKEN_MIXPANEL;
import static com.pagatodo.yaganaste.utils.Recursos.URL_BD_ODIN;

/**
 * Created by flima on 17/03/17.
 */

public class App extends Application {

    private static App m_singleton;
    private static AppDatabase m_database;
    private static DatabaseReference m_reference;
    private CountDownTimer countDownTimer;
    private SupportFragmentActivity currentActivity;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public QPOSService pos;
    public IposListener emvListener;
    public static MixpanelAPI mixpanel;
    private Preferencias prefs;
    //currentMount
    private String currentMount;
    private boolean cancel;

    private String datoHuellaC;
    private ApplicationLifecycleHandler lifecycleHandler;
    private Map<String, Activity> quoeeuActivites;
    private String currentSaldo;

    public static App getInstance() {
        return m_singleton;
    }

    public static Context getContext() {
        return m_singleton.getApplicationContext();
    }

    public static AppDatabase getAppDatabase() {
        return m_database = AppDatabase.getInMemoryDatabase(getContext());
    }

    public static DatabaseReference getDatabaseReference() {
        return m_reference = FirebaseDatabase.getInstance(URL_BD_ODIN).getReference();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        currentSaldo = "0.00";
        String languageToLoad = "es"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        m_singleton = this;
        MultiDex.install(this);

        this.prefs = new Preferencias(this);
        System.loadLibrary("a01jni");
        //initEMVListener(QPOSService.CommunicationMode.AUDIO);
        RequestHeaders.initHeaders(this);
        lifecycleHandler = new ApplicationLifecycleHandler();
        registerActivityLifecycleCallbacks(lifecycleHandler);
        registerComponentCallbacks(lifecycleHandler);

        new ScreenReceiver(getContext(), lifecycleHandler);
        quoeeuActivites = new LinkedHashMap<>();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        // Se obtiene el Token de Firebase si no existe
        /*if (prefs.loadData(FIREBASE_KEY).equals("")) {
            String s = FirebaseInstanceId.getInstance().getToken();
            prefs.saveData(FIREBASE_KEY, s);
        }*/
        // Se crea la carpeta donde almacenar los Screenshot para compartir.
        File f = new File(Environment.getExternalStorageDirectory() + getString(R.string.path_image));
        if (!f.exists()) {
            f.mkdir();
        }
        if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
            Log.i(getString(R.string.app_name), "Android Device Id: " + Utils.getUdid(getContext()));
        }
        datoHuellaC = "";
        //Contly
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
            mixpanel = MixpanelAPI.getInstance(this, TOKEN_MIXPANEL);
        } else {
            Stetho.initializeWithDefaults(this);
        }
    }

    public void clearCache() {
        try {
            File dir = getContext().getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    //Curren Mount
    public String getCurrentMount() {
        return currentMount;
    }

    public void setCurrentMount(String currentMount) {
        this.currentMount = currentMount;
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
    public void initEMVListener(QPOSService.CommunicationMode mode) {
        emvListener = new IposListener(getApplicationContext());
        pos = QPOSService.getInstance(mode);
        pos.setConext(getApplicationContext());
        pos.setVolumeFlag(false);
        Handler handler = new Handler(Looper.myLooper());
        pos.initListener(handler, emvListener);
        pos.getSdkVersion();
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
        try {
            ApiAdtvo.cerrarSesion();// Se envia null ya que el Body no aplica.
        } catch (OfflineException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(SELECTION, MAIN_SCREEN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
            Log.e("APP", "Close From: " + Thread.currentThread().getStackTrace()[1].getMethodName());
        }

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
        countDownTimer = new CountDownTimer(SESSION_TIMEOUT, SESSION_TIMEOUT) {
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

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public static void setBadge(int count) {
        String launcherClassName = getLauncherClassName(getContext());
        if (launcherClassName == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", getContext().getPackageName());
        intent.putExtra("badge_count_class_name", launcherClassName);
        getContext().sendBroadcast(intent);
    }

    public static String getLauncherClassName(Context context) {
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            String pkgName = resolveInfo.activityInfo.applicationInfo.packageName;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                String className = resolveInfo.activityInfo.name;
                return className;
            }
        }
        return null;
    }

    public void downloadFile(String urlData, String nameData, String
            typeData, FileDownloadListener fileDownloadListener) {
        FileDownload fileDownload = new FileDownload(urlData,
                nameData, typeData, fileDownloadListener);
        fileDownload.execute("");
    }

    public String getCurrentSaldo() {
        return currentSaldo;
    }

    public void setCurrentSaldo(String currentSaldo) {
        this.currentSaldo = currentSaldo;
    }
}
