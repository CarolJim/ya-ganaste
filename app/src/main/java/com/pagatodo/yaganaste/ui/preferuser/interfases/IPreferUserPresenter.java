package com.pagatodo.yaganaste.ui.preferuser.interfases;

import android.graphics.Bitmap;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.utils.camera.CameraManager;

/**
 * Created by Francisco Manzo on 08/06/2017.
 */

public interface IPreferUserPresenter {
    void DesasociarToPresenter();

    void sendSuccessPresenter(String mensaje);

    void sendErrorPresenter(String mensaje);

    void sendErrorServerPresenter(String error);

    void getImagenURLPresenter(String mUserImage);

    void sendImageBitmapPresenter(Bitmap bitmap);

    void openMenuPhoto(int i, CameraManager cameraManager);

    void sendPresenterActualizarAvatar(ActualizarAvatarRequest avatarRequest);

    void onFailPresenter();

    void sucessUpdateAvatar();

    void sendErrorAvatarPresenter(String mensaje);
}
