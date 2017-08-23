package com.pagatodo.yaganaste.ui.preferuser.interfases;

import android.graphics.Bitmap;

/**
 * Created by Francisco Manzo on 22/05/2017.
 */

public interface IListaOpcionesView extends IPreferUserGeneric {
    void setPhotoToService(Bitmap bitmap);

    void showProgress(String mMensaje);

    void showExceptionToView(String mMesage);

    void sendSuccessAvatarToView(String mensaje);

    void sendErrorAvatarToView(String mensaje);

    void sendErrorEnvioCorreoContactanos(String mensaje);
}
