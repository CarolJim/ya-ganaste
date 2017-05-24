package com.pagatodo.yaganaste.ui.preferuser.interfases;

import android.graphics.Bitmap;

import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;

/**
 * Created by Francisco Manzo on 23/05/2017.
 */

public interface IListaOpcionesIteractor {
    void sendIteractorActualizarAvatar(ActualizarAvatarRequest avatarRequest);

    void getImagenURLiteractor(String mUserImage);

    void sendToIteractorBitmap(Bitmap bitmap);
}
