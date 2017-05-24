package com.pagatodo.yaganaste.ui.preferuser.interfases;

import android.graphics.Bitmap;

/**
 * Created by Francisco Manzo on 22/05/2017.
 */

public interface IListaOpcionesView {
    void setPhotoToService(Bitmap bitmap);

    void sucessUpdateAvatar();

    void sendErrorView(String mensaje);

    void showProgress(String mMensaje);

    void sendImageBitmapView(Bitmap bitmap);

    void onFailView();
}
