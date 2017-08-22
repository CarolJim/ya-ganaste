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

    void sendErrorServerPresenter(String error);

    void openMenuPhoto(int i, CameraManager cameraManager);

    void sendPresenterActualizarAvatar(ActualizarAvatarRequest avatarRequest);

    void showExceptionAvatarToPresenter(String s);

    void showExceptionToPresenter(String mMesage);

    void sendErrorServerAvatarToPresenter(String s);

    void changeEmailToPresenter(String s, String s1);

    void successGenericToPresenter(DataSourceResult dataSourceResult);

    void errorGenericToPresenter(DataSourceResult dataSourceResult);

    void changePassToPresenter(String s, String s1);

    void sendErrorServerPassToPresenter(String s);

    void showExceptionPassToPresenter(String s);

    void sendErrorServerDesasociarToPresenter(String s);

    void showExceptionDesasociarToPresenter(String s);

    void toPresenterBloquearCuenta(int i);

    void showExceptionBloquearCuentaToPresenter(String s);

    void sendErrorServerBloquearCuentaToPresenter(String s);
}
