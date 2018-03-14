package com.pagatodo.yaganaste.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Gorro on 20/11/16.
 */

public class BitmapLoader extends AsyncTask {

    private static final String TAG = BitmapLoader.class.getSimpleName();
    private IBitmapBase64Listener listener;
    private Context context;
    private String imgBase64;
    private String filePath;
    private Bitmap bitmap;

    public BitmapLoader(Bitmap bitmap, IBitmapBase64Listener listener) {
        this.listener = listener;
        this.bitmap = bitmap;
    }

    public BitmapLoader(Context context, String filePath, IBitmapBase64Listener listener) {
        this.context = context;
        this.filePath = filePath;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.listener.onBegin();
    }

    @Override
    protected Bitmap doInBackground(Object... params) {
        try {
            if (bitmap != null) {
                imgBase64 = bitmapToBase64(bitmap);
                return bitmap;
            } else {
                // Bitmap btm = Compressor.getDefault(context).compressToBitmap(new File(filePath));
                Bitmap btm = BitmapFactory.decodeFile(filePath);
                int anchooriginal = btm.getWidth();
                int altooriginal = btm.getHeight();

                if (altooriginal > anchooriginal) {
                    btm = btm.createScaledBitmap(btm, 500, 888, false);
                } else {
                    btm = btm.createScaledBitmap(btm, 888, 500, false);
                }
                imgBase64 = bitmapToBase64(btm);
                return btm;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Bitmap bm = ((Bitmap) o);
        listener.OnBitmap64Listener(bm, imgBase64);
        if (!bm.isRecycled()) {
            bm.recycle();
        }
    }

    public String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(filePath.getBytes().length);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] bytes = outputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}
