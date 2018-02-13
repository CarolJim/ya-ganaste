package com.pagatodo.yaganaste.ui_wallet.presenter;

import android.widget.Toast;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.interfaces.INotificationHistory;
import com.pagatodo.yaganaste.ui_wallet.fragments.NotificationHistoryFragment;
import com.pagatodo.yaganaste.ui_wallet.interactors.UserNotificationInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IUserNotificationInteractor;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IUserNotificationPresenter;

/**
 * Created by FranciscoManzo on 13/02/2018.
 */

public class UserNotificationPresenter implements IUserNotificationPresenter,
        IUserNotificationInteractor.OnUserNotifListener {

    INotificationHistory mView;
    IUserNotificationInteractor mInteractor;

    public UserNotificationPresenter(INotificationHistory mView,
                                     IUserNotificationInteractor mInteractor) {
        this.mView = mView;
        this.mInteractor = mInteractor;
    }

    @Override
    public void createTest() {
        mInteractor.test(1, 2, this);
    }

    @Override
    public void onSuccess() {
        Toast.makeText(App.getContext(), "onSuccess", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError() {
        Toast.makeText(App.getContext(), "onError", Toast.LENGTH_SHORT).show();
    }
}
