package com.pagatodo.yaganaste.ui._manager;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.interfaces.ISessionExpired;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserGeneric;

/**
 * Created by Francisco Manzo on 06/07/2017.
 * Presenter padre que permite el uso del metodo sessionExpiredToPresenter en todos los hijos,
 * para pode rcomunicar el iteractor con cualquier vista, a su vez, que el GenericFRagment pueda
 * disara el PopMenu que finaliza la app
 *
 * Usamos la notacion <T extends ISessionExpired> para especificar que vamos a recibir cualquier
 * decendiente generico de la interfase ISessionExpired
 */

public class GenericPresenterMain<T extends ISessionExpired> {

    ISessionExpired iPreferUserGeneric;
    public GenericPresenterMain() {

    }

    /**
     * Usamos esta notacion para especificar que vamos a recibir cualquier generico de
     * interfase ISessionExpired
     * @param iPreferUserGeneric
     */
    public void setIView(T iPreferUserGeneric) {
        this.iPreferUserGeneric = iPreferUserGeneric;
    }

    /**
     * De la vista actual, mandamos el DataSourceResult. Por medio de herencia lo comunicamos con su
     * padre, para que este proceses el dialogo siguiente
     * @param dataSourceResult
     */
    public void sessionExpiredToPresenter(DataSourceResult dataSourceResult){
        iPreferUserGeneric.errorSessionExpired(dataSourceResult);
    }
}
