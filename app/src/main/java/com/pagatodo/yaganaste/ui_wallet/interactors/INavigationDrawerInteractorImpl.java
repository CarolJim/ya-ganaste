package com.pagatodo.yaganaste.ui_wallet.interactors;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActualizarAvatarResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.CerrarSesionResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.interfaces.IRequestResult;
import com.pagatodo.yaganaste.interfaces.enums.WebService;
import com.pagatodo.yaganaste.net.ApiAdtvo;
import com.pagatodo.yaganaste.net.RequestHeaders;
import com.pagatodo.yaganaste.ui_wallet.interfaces.INavigationDrawerInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.NavigationDrawerPresenter;
import com.pagatodo.yaganaste.utils.Recursos;

import static com.pagatodo.yaganaste.interfaces.enums.WebService.ACTUALIZAR_AVATAR;
import static com.pagatodo.yaganaste.utils.Recursos.CODE_SESSION_EXPIRED;
import static com.pagatodo.yaganaste.utils.Recursos.CONSULT_FAVORITE;
import static com.pagatodo.yaganaste.utils.StringUtils.procesarURLString;

/**
 * Created by icruz on 21/03/2018.
 */

public class INavigationDrawerInteractorImpl implements INavigationDrawerInteractor, IRequestResult {

    private boolean logOutBefore;
    private NavigationDrawerPresenter navigationDrawerPresenter;

    public INavigationDrawerInteractorImpl(NavigationDrawerPresenter navigationDrawerPresenter) {
        this.navigationDrawerPresenter = navigationDrawerPresenter;
    }

    @Override
    public void sendIteractorActualizarAvatar(ActualizarAvatarRequest avatarRequest) {
        try {
            ApiAdtvo.actualizarAvatar(avatarRequest, this);
        } catch (OfflineException e) {
            // e.printStackTrace();
            navigationDrawerPresenter.showExceptionAvatarToPresenter(e.toString());
        }

    }

    @Override
    public void logOutSession() {
        logOutBefore = true;
        try {
            ApiAdtvo.cerrarSesion(this);
        } catch (OfflineException e) {
            // e.printStackTrace();
            navigationDrawerPresenter.showExceptionToPresenter(e.toString());
        }
    }

    //RESUL REQUEST


    @Override
    public void onSuccess(DataSourceResult dataSourceResult) {
        if (dataSourceResult.getData() instanceof CerrarSesionResponse) {
            //Log.d("PreferUserIteractor", "DataSource Sucess Server Error CerrarSesion");
            RequestHeaders.setTokensesion("");//Reseteamos el token de sesión
            App.getInstance().getPrefs().saveDataBool(CONSULT_FAVORITE, false);
            //CerrarSesionRequest response = (CerrarSesionRequest) dataSourceResult.getData();
            navigationDrawerPresenter.successGenericToPresenter(dataSourceResult);
        }

        /**
         * Instancia de peticion exitosa de ActualizarAvatarResponse
         */
        if (dataSourceResult.getData() instanceof ActualizarAvatarResponse) {
            ActualizarAvatarResponse response = (ActualizarAvatarResponse) dataSourceResult.getData();
            if (response.getCodigoRespuesta() == Recursos.CODE_OK) {
                String urlEdit = procesarURLString(response.getData().getImagenAvatarURL());
                SingletonUser.getInstance().getDataUser().getUsuario().setImagenAvatarURL(urlEdit);
                navigationDrawerPresenter.successGenericToPresenter(dataSourceResult);

            } else if (((GenericResponse) dataSourceResult.getData()).getCodigoRespuesta() == CODE_SESSION_EXPIRED) {
                //preferUserPresenter.sessionExpiredToPresenter(dataSourceResult);
            } else {
                navigationDrawerPresenter.errorGenericToPresenter(dataSourceResult);
            }
        }
    }

    @Override
    public void onFailed(DataSourceResult error) {
        if (error.getWebService().equals(ACTUALIZAR_AVATAR)) {
            navigationDrawerPresenter.sendErrorServerAvatarPresenter(error.getData().toString());
        }

    }
}
