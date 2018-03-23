package com.pagatodo.yaganaste.ui_wallet.presenter;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActualizarAvatarResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CerrarSesionResponse;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IListaOpcionesView;
import com.pagatodo.yaganaste.ui_wallet.interactors.INavigationDrawerInteractorImpl;
import com.pagatodo.yaganaste.ui_wallet.interfaces.INavigationDrawerInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.NavigationDrawerPresenter;
import com.pagatodo.yaganaste.utils.camera.CameraManager;

import static com.pagatodo.yaganaste.utils.Recursos.URL_PHOTO_USER;

/**
 * Created by icruz on 21/03/2018.
 */

public class NavigationDrawerPresenterImpl implements NavigationDrawerPresenter {

    private IListaOpcionesView iListaOpcionesView;
    private INavigationDrawerInteractor iNavigationDrawerInteractor;

    public NavigationDrawerPresenterImpl(IListaOpcionesView iListaOpcionesView) {
        this.iListaOpcionesView = iListaOpcionesView;
        this.iNavigationDrawerInteractor = new INavigationDrawerInteractorImpl(this);
    }

    @Override
    public void openMenuPhoto(int i, CameraManager cameraManager) {
        try {
            cameraManager.createPhoto(i);
        } catch (Exception e) {
            iListaOpcionesView.showExceptionToView(e.toString());
        }
    }

    @Override
    public void logOutSession() {
        iNavigationDrawerInteractor.logOutSession();
    }

    @Override
    public void sendPresenterActualizarAvatar(ActualizarAvatarRequest avatarRequest) {
        iNavigationDrawerInteractor.sendIteractorActualizarAvatar(avatarRequest);
    }

    @Override
    public void showExceptionAvatarToPresenter(String message) {
        iListaOpcionesView.sendErrorAvatarToView(message);
    }

    @Override
    public void successGenericToPresenter(DataSourceResult dataSourceResult) {
        if (dataSourceResult.getData() instanceof CerrarSesionResponse) {
            if (iListaOpcionesView != null) {
                iListaOpcionesView.setLogOutSession();
            }
        }

        if (dataSourceResult.getData() instanceof ActualizarAvatarResponse) {
            ActualizarAvatarResponse response = (ActualizarAvatarResponse) dataSourceResult.getData();
            String mUserImage = response.getData().getImagenAvatarURL();
            String[] urlSplit = mUserImage.split("_");
            if (urlSplit.length > 1) {
                App.getInstance().getPrefs().saveData(URL_PHOTO_USER, urlSplit[0] + "_M.png");
            }
            iListaOpcionesView.sendSuccessAvatarToView(response.getMensaje());
        }
    }

    @Override
    public void showExceptionToPresenter(String mMesage) {
        iListaOpcionesView.showExceptionToView(mMesage);
    }

    @Override
    public void sessionExpiredToPresenter(DataSourceResult dataSourceResult) {
        //iPreferUserGeneric.errorSessionExpired(dataSourceResult);
    }

    @Override
    public void errorGenericToPresenter(DataSourceResult dataSourceResult) {
        if (dataSourceResult.getData() instanceof ActualizarAvatarResponse) {
            ActualizarAvatarResponse response = (ActualizarAvatarResponse) dataSourceResult.getData();
            iListaOpcionesView.sendErrorAvatarToView(response.getMensaje());
        }
    }

    @Override
    public void sendErrorServerAvatarPresenter(String s) {
        iListaOpcionesView.sendErrorAvatarToView(s);
    }
}
