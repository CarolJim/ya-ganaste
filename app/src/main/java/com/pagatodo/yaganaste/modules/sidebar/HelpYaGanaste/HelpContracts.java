package com.pagatodo.yaganaste.modules.sidebar.HelpYaGanaste;

import com.pagatodo.yaganaste.interfaces.enums.Direction;

public class HelpContracts {
    public interface Router{
        void showHelpChat(Direction direction);
        void showHelpEmail(Direction direction);
        void showHelpPhone(Direction direction);
    }
}
