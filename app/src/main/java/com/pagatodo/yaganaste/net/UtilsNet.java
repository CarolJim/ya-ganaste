package com.pagatodo.yaganaste.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;

/**
 * Created by flima on 16/02/2017.
 * <p>
 * Clase con los métodos de utilidad para las operaciones con WS.
 */

public class UtilsNet {

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo network_info = cm.getActiveNetworkInfo();
        return network_info != null && network_info.isConnected() ? true : false;
    }

    private static String encodeUrl(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            throw new IllegalArgumentException(uee);
        }
    }

    public static Object jsonToObject(String json, Type type) {

        Object o = null;
        try {
            o = new GsonBuilder()
                    .setDateFormat("dd-MM-yyyy HH:mm:ss")
                    .create().
                            fromJson(json, type);
        } catch (Exception e) {
            o = null;
        }
        return o;
    }
}
