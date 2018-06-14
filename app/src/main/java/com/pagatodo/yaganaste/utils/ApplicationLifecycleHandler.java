package com.pagatodo.yaganaste.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.net.VolleySingleton;
import com.pagatodo.yaganaste.ui._controllers.MainActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;

import io.card.payment.CardIOActivity;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;
import static com.pagatodo.yaganaste.utils.Recursos.SHOW_LOGS_PROD;

/**
 * Created by Jordan on 18/07/2017.
 */

public class ApplicationLifecycleHandler implements Application.ActivityLifecycleCallbacks,
        ComponentCallbacks2, IRequestResult {

    private final String TAG = ApplicationLifecycleHandler.class.getSimpleName();
    private boolean isInBackground = false;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        App.getInstance().addToQuee(activity);
    }


    @Override
    public void onActivityResumed(Activity activity) {
        if (!(activity instanceof CardIOActivity)) {
            App.getInstance().resetTimer((SupportFragmentActivity) activity);
            if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                Log.e(TAG, "Reset From: " + activity.getClass().getSimpleName());
            }
            isInBackground = false;
        }
    }

    private void goToLoginScreen(Activity activity) {
        VolleySingleton.getInstance(App.getContext()).deleteQueue();

        try {
            ApiAdtvo.cerrarSesion(this);// Se envia null ya que el Body no aplica.
        } catch (OfflineException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(SELECTION, MAIN_SCREEN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }


    @Override
    public void onActivityStopped(Activity activity) {

    }


    @Override
    public void onActivityDestroyed(Activity activity) {

    }


    @Override
    public void onTrimMemory(int level) {
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN || level == ComponentCallbacks2.TRIM_MEMORY_COMPLETE || level == ComponentCallbacks2.TRIM_MEMORY_MODERATE) {
            isInBackground = true;
            App.getInstance().resetTimer();
            if (App.getInstance().getPrefs().loadDataBoolean(SHOW_LOGS_PROD, false)) {
                Log.e(TAG, "Reset From: " + "Back");
            }
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
    }

    @Override
    public void onFailed(DataSourceResult error) {
    }

    public boolean isInBackground() {
        return isInBackground;
    }

    public void setInBackground(boolean inBackground) {
        isInBackground = inBackground;
    }
}
