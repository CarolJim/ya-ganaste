package com.pagatodo.yaganaste.ui.preferuser.interfases;

import android.graphics.Bitmap;

import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;

/**
 * Created by Francisco Manzo on 22/05/2017.
 */

public interface IListaOpcionesView extends IPreferUserGeneric{
    void setPhotoToService(Bitmap bitmap);

    void sucessUpdateAvatar();

    void sendErrorView(String mensaje);

    void showProgress(String mMensaje);

    void sendImageBitmapView(Bitmap bitmap);

    void onFailView();

    void showExceptionToView(String mMesage);
}
