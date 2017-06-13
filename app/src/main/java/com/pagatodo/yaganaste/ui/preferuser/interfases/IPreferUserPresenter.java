package com.pagatodo.yaganaste.ui.preferuser.interfases;

import com.pagatodo.yaganaste.data.DataSourceResult;

/**
 * Created by Francisco Manzo on 08/06/2017.
 */

public interface IPreferUserPresenter {
    void DesasociarToPresenter();

    void sendSuccessPresenter(String mensaje);

    void sendErrorPresenter(String mensaje);

    void sendErrorServerPresenter(String error);
}
