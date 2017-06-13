package com.pagatodo.yaganaste.ui.preferuser.interfases;

import com.pagatodo.yaganaste.data.DataSourceResult;

/**
 * Created by Francisco Manzo on 08/06/2017.
 */

public interface IPreferDesasociarView extends  IPreferUserTest {

    void sendSuccessView(String mensaje);

    void sendErrorView(String mensaje);

    void sendErrorServerView(String error);
}
