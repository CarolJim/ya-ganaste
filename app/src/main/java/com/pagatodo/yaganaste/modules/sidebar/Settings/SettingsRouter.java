package com.pagatodo.yaganaste.modules.sidebar.Settings;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.sidebar.SettingsOfSecurity.SecuritySettignsFragment;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;

public class SettingsRouter implements SettingsContracts.Router {

    private PreferUserActivity activity;

    public SettingsRouter(PreferUserActivity activity) {
        this.activity = activity;
    }

    @Override
    public void showSettingsOfSecurity(Direction direction) {
        activity.loadFragment(SecuritySettignsFragment.newInstance(),R.id.container,false);
    }

    @Override
    public void showConfigureCardReader(Direction direction) {

    }

    @Override
    public void showUnlikePhone(Direction direction) {

    }

    @Override
    public void showCancelAccount(Direction direction) {

    }
}
