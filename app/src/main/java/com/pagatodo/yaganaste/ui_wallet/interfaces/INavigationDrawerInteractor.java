package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActualizarAvatarRequest;

/**
 * Created by icruz on 21/03/2018.
 */

public interface INavigationDrawerInteractor {

    void sendIteractorActualizarAvatar(ActualizarAvatarRequest avatarRequest);
    void logOutSession();
}
