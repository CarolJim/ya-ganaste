package com.pagatodo.yaganaste.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

import com.pagatodo.yaganaste.ui.preferuser.iteractors.ListaOpcionesIteractor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import id.zelory.compressor.Compressor;

/**
 * Created by Gorro on 20/11/16.
 */

public class BitmapDownload extends AsyncTask<String, Object, Bitmap> {

    private static final String TAG = BitmapDownload.class.getSimpleName();
    private BitmapBase64Listener listener;
    private Context context;
    private String imgBase64;
    private String filePath;
    private Bitmap bitmap;

    private String mUserImage;
    private ListaOpcionesIteractor mIteractor;

    public BitmapDownload(Bitmap bitmap, BitmapBase64Listener listener) {
        this.listener = listener;
        this.bitmap = bitmap;
    }

    public BitmapDownload(Context context, String filePath, BitmapBase64Listener listener) {
        this.context = context;
        this.filePath = filePath;
        this.listener = listener;
    }

    public BitmapDownload(String mUserImage, ListaOpcionesIteractor mIteractor) {
        this.mUserImage = mUserImage;
        this.mIteractor = mIteractor;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            java.net.URL url = new java.net.URL(mUserImage);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        mIteractor.sendToIteractorBitmap(bitmap);

    }
}
