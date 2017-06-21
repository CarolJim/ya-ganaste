package com.pagatodo.yaganaste.ui.preferuser.interfases;

import android.graphics.Bitmap;

/**
 * Created by Francisco Manzo on 22/05/2017.
 */

public interface IMyEmailView extends IPreferUserGeneric{
    void sendSuccessEmailToView(String mensaje);

    void sendErrorEmailToView(String mensaje);
}
