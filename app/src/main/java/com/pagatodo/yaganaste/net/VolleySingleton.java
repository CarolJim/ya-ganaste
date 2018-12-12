package com.pagatodo.yaganaste.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.thoughtcrime.ssl.pinning.PinningSSLSocketFactory;

import static com.pagatodo.yaganaste.net.ApiAdq.PIN_ADQ;
import static com.pagatodo.yaganaste.net.ApiAdtvo.PIN_ADTVO;
import static com.pagatodo.yaganaste.net.ApiStarbucks.PIN_STARBUCKS;
import static com.pagatodo.yaganaste.net.ApiTrans.PIN_TRANS;

/**
 * Created on 16/02/2017.
 *
 * @author flima
 * @version 1.0
 * Clase Singleton para obtener una instancia de Volley
 */
public class VolleySingleton {

    private static VolleySingleton mInstance;
    private static Context mCtx;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private boolean needPin = true;
    public static String REQUEST_TAG = "VOLLEY_REQUEST_TAG";

    /**
     * Método para obtener una instancia de Volley
     *
     * @param context {@link Context}
     */
    private VolleySingleton(Context context, boolean pin) {
        mCtx = context;
        if (pin) {
            mRequestQueue = getRequestQueue();
        } else {
            mRequestQueue = getRequestQueueNoPin();
        }

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
            mInstance = new VolleySingleton(context, true);
        }
        return mInstance;
    }

    public static synchronized VolleySingleton getInstance(Context context, boolean pin) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(context, pin);
        }
        return mInstance;
    }

    /**
     * Método para obtener una instancia de {@link RequestQueue}
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            String[] pins = new String[]{PIN_ADTVO, PIN_TRANS, PIN_ADQ, PIN_STARBUCKS};
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
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext(), new HttpClientStack(httpClient));
        }
        return mRequestQueue;
    }

    public RequestQueue getRequestQueueNoPin() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(REQUEST_TAG);
        getRequestQueue().add(req);
    }

    public void deleteQueue() {
        getRequestQueue().cancelAll(REQUEST_TAG);
        mRequestQueue = null;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }


}