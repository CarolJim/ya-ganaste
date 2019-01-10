package com.pagatodo.yaganaste.modules.sidebar.SettingsOfSecurity;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.wallet_emisor.ChangeNip.MyChangeNip;
import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui._controllers.TabActivity;

public class SidebarRouter implements SIdebarContracts.Router {

    private PreferUserActivity activity;

    SidebarRouter(PreferUserActivity activity){
        this.activity=activity;
    }

    @Override
    public void showChangeNip(Direction direction) {
        activity.loadFragment(MyChangeNip.newInstance(),R.id.fragment_container,direction,
        false);
    }
}
