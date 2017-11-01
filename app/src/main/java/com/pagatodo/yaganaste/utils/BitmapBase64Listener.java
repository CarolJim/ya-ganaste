package com.pagatodo.yaganaste.utils;

import android.graphics.Bitmap;

/**
 * @author Gorro
 */

public interface BitmapBase64Listener {
    void onBegin();
    void OnBitmap64Listener(Bitmap bitmap, String imgbase64);
}
