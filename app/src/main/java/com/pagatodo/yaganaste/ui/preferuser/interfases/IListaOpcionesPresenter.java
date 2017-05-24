package com.pagatodo.yaganaste.ui.preferuser.interfases;

import android.graphics.Bitmap;

import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.utils.customviews.UploadDocumentView;

/**
 * Created by Francisco Manzo on 22/05/2017.
 */

public interface IListaOpcionesPresenter {
    void openMenuPhoto(int i);

    void sendPresenterActualizarAvatar(ActualizarAvatarRequest avatarRequest);

    void sucessUpdateAvatar();

    void sendErrorPresenter(String mensaje);

    void getImagenURLPresenter(String mUserImage);

    void sendImageBitmapPresenter(Bitmap bitmap);
}
