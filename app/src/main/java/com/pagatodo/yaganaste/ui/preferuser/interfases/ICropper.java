package com.pagatodo.yaganaste.ui.preferuser.interfases;

import android.net.Uri;

import com.pagatodo.yaganaste.utils.BitmapLoader;
import com.steelkiwi.cropiwa.CropIwaView;
import com.steelkiwi.cropiwa.image.CropIwaResultReceiver;

import java.io.Serializable;

/**
 * Created by icruz on 06/10/2017.
 */

public interface ICropper{

    void onCropper(Uri uri);
    void onHideProgress();
    void failLoadJpg();
}
