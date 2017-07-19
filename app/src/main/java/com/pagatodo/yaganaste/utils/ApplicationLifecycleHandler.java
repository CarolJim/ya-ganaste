package com.pagatodo.yaganaste.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;

import com.pagatodo.yaganaste.ui._controllers.AccountActivity;
import com.pagatodo.yaganaste.ui._controllers.LandingAdqFragment;
import com.pagatodo.yaganaste.ui._controllers.MainActivity;
import com.pagatodo.yaganaste.ui._controllers.SplashActivity;
import com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity;
import com.pagatodo.yaganaste.ui.account.register.LandingFragment;

import static com.pagatodo.yaganaste.ui.account.login.MainFragment.MAIN_SCREEN;
import static com.pagatodo.yaganaste.ui.account.login.MainFragment.SELECTION;

/**
 * Created by Jordan on 18/07/2017.
 */

public class ApplicationLifecycleHandler implements Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {

    private boolean isInBackground = false;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (isInBackground && !(activity instanceof MainActivity || activity instanceof AccountActivity || activity instanceof SplashActivity)) {
            if ((activity instanceof LandingAdqFragment || activity instanceof LandingFragment)) {
                goToLoginScreen(activity);
            } else if (!((SupportFragmentActivity) activity).isFromActivityForResult()) {
                goToLoginScreen(activity);
            }
        }
        isInBackground = false;
    }

    private void goToLoginScreen(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(SELECTION, MAIN_SCREEN);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    public void onTrimMemory(int level) {
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN || level == ComponentCallbacks2.TRIM_MEMORY_COMPLETE || level == ComponentCallbacks2.TRIM_MEMORY_MODERATE) {
            isInBackground = true;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }

    @Override
    public void onLowMemory() {

    }
}
