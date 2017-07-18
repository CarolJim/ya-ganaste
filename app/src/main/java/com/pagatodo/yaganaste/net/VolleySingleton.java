package com.pagatodo.yaganaste.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.pagatodo.yaganaste.utils.Recursos;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.thoughtcrime.ssl.pinning.PinningSSLSocketFactory;

/**
 * Created on 16/02/2017.
 *
 * @author flima
 * @version 1.0
 *          Clase Singleton para obtener una instancia de Volley
 */
public class VolleySingleton {

    private static VolleySingleton mInstance;
    private static Context mCtx;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    /**
     * Método para obtener una instancia de Volley
     *
     * @param context {@link Context}
     */
    private VolleySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    /**
     * Método para obtener una instancia de {@link RequestQueue}
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {

            String[] pins = new String[]{Recursos.PIN_ADVO, Recursos.PIN_TRANS, Recursos.PIN_YA};
            HttpParams httpParameters = new BasicHttpParams();

            SchemeRegistry schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

            try {
                schemeRegistry.register(new Scheme("https", new PinningSSLSocketFactory(mCtx.getApplicationContext(), pins, 0), 443));
            } catch (Exception e) {
                e.printStackTrace();
            }

            ClientConnectionManager connectionManager = new ThreadSafeClientConnManager(httpParameters, schemeRegistry);
            DefaultHttpClient httpClient = new DefaultHttpClient(connectionManager, httpParameters);


            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext(), new HttpClientStack(httpClient));
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}