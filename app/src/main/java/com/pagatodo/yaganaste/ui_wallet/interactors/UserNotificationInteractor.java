package com.pagatodo.yaganaste.ui_wallet.interactors;

import com.pagatodo.yaganaste.ui_wallet.interfaces.IUserNotificationInteractor;
import com.pagatodo.yaganaste.ui_wallet.presenter.UserNotificationPresenter;

/**
 * Created by FranciscoManzo on 13/02/2018.
 */

public class UserNotificationInteractor implements IUserNotificationInteractor {
    @Override
    public void test(int i, int i1, UserNotificationPresenter mPresenter) {
        if(i == 1){
            mPresenter.onSuccess();
        }else{
            mPresenter.onError();
        }
    }
}
