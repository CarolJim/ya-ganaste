package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.ui_wallet.presenter.UserNotificationPresenter;

/**
 * Created by FranciscoManzo on 13/02/2018.
 */

public interface IUserNotificationInteractor {

    interface OnUserNotifListener{
        void onSuccess();
        void onError();
    }

    void test(int i, int i1, UserNotificationPresenter userNotificationPresenter);
}
