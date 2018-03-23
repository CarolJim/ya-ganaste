package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.utils.camera.CameraManager;

/**
 * Created by icruz on 21/03/2018.
 */

public interface NavigationDrawerPresenter {

    void openMenuPhoto(int i, CameraManager cameraManager);
    void logOutSession();
    void sendPresenterActualizarAvatar(ActualizarAvatarRequest avatarRequest);
    void showExceptionAvatarToPresenter(String s);
    void successGenericToPresenter(DataSourceResult dataSourceResult);
    void showExceptionToPresenter(String mMesage);
    void sessionExpiredToPresenter(DataSourceResult dataSourceResult);
    void errorGenericToPresenter(DataSourceResult dataSourceResult);
    void sendErrorServerAvatarPresenter(String s);
}
