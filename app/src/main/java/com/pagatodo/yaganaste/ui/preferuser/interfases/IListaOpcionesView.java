package com.pagatodo.yaganaste.ui.preferuser.interfases;

import android.graphics.Bitmap;

import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;

/**
 * Created by Francisco Manzo on 22/05/2017.
 */

public interface IListaOpcionesView extends IPreferUserGeneric{
    void setPhotoToService(Bitmap bitmap);

    void showProgress(String mMensaje);

    void sendImageBitmapToView(Bitmap bitmap);

    void showExceptionToView(String mMesage);

    void sendSuccessAvatarToView(String mensaje);

    void sendErrorAvatarToView(String mensaje);
}
