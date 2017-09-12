package com.pagatodo.yaganaste.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.pagatodo.yaganaste.App;

/**
 * Created by Jordan on 28/08/2017.
 */

public class ScreenReceiver extends BroadcastReceiver {

    private ApplicationLifecycleHandler lifecycleHandler;

    public ScreenReceiver(Context ctx, ApplicationLifecycleHandler lifecycleHandler) {
        // register receiver that handles screen on and screen off logic
        IntentFilter filter = new IntentFilter();
        //filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        this.lifecycleHandler = lifecycleHandler;
        ctx.registerReceiver(ScreenReceiver.this, filter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        lifecycleHandler.setInBackground(true);
        App.getInstance().resetTimer();
    }
}
