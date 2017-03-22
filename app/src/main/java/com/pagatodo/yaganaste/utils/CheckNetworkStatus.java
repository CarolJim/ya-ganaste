package com.pagatodo.yaganaste.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Gorro on 08/08/16.
 */
public class CheckNetworkStatus {

    private static ConnectivityManager connectivityManager;

    public static void init(Context ctx) {
        connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

    }

    public static boolean isConnected() {
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

}
