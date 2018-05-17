package com.pagatodo.yaganaste.ui_wallet.interfaces;

import com.pagatodo.yaganaste.interfaces.INavigationView;

/**
 * Created by asandovals on 19/04/2018.
 */

public interface Iloginstarbucks extends INavigationView {
    void loginstarsucced();
    void loginfail(String mensaje);

    void forgetpasswordsucced();
    void forgetpasswordfail(String mensaje);
}
