package com.pagatodo.yaganaste.modules.sidebar.Settings;

import com.pagatodo.yaganaste.interfaces.enums.Direction;

public class SettingsContracts {
    public interface Router{
        void showSettingsOfSecurity(Direction direction);
        void showConfigureCardReader(Direction direction);
        void showUnlikePhone(Direction direction);
        void showCancelAccount(Direction direction);
    }
}
