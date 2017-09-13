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
import com.pagatodo.yaganaste.net.IRequestResult;
import com.pagatodo.yaganaste.net.VolleySingleton;
import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._controllers.LandingActivity;
import com.pagatodo.yaganaste.ui._controllers.LandingApprovedActivity;
import com.pagatodo.yaganaste.ui._controllers.MainActivity;
import com.pagatodo.yaganaste.ui._controllers.OnlineTxActivity;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._controllers.ScannVisionActivity;
import com.pagatodo.yaganaste.ui._controllers.SplashActivity;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;

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
        /*if (isInBackground &&
                !(activity instanceof MainActivity || activity instanceof AccountActivity
                        || activity instanceof SplashActivity || activity instanceof LandingApprovedActivity || activity instanceof OnlineTxActivity)) {
            if ((activity instanceof LandingActivity || activity instanceof ScannVisionActivity)) {
                goToLoginScreen(activity);
            } else if (!((SupportFragmentActivity) activity).isFromActivityForResult()) {
                goToLoginScreen(activity);
            } else if ((activity instanceof LandingApprovedActivity)) {

            }else if ((activity instanceof TabActivity)) {

            }else if ((activity instanceof PreferUserActivity)) {

            }
        }*/

        App.getInstance().resetTimer((SupportFragmentActivity)activity);
        Log.e(TAG, "Reset From: " + activity.getClass().getSimpleName());
        isInBackground = false;
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
            Log.e(TAG, "Reset From: " + "Back");
         /*   if (isInBackground && !(activity instanceof MainActivity || activity instanceof AccountActivity || activity instanceof SplashActivity)
                    && !((SupportFragmentActivity) activity).isFromActivityForResult()) {
                // Consumimos de manera directa el servicio de cerrar session
                try {
                    ApiAdtvo.cerrarSesion(this);// Se envia null ya que el Body no aplica.
                } catch (OfflineException e) {
                    e.printStackTrace();
                }
            }*/
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
