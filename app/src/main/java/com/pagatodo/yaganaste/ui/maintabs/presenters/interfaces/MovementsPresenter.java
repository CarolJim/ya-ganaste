package com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces;

import com.pagatodo.yaganaste.interfaces.IEnumTab;

/**
 * @author Juan Guerra on 28/03/2017.
 */

public interface MovementsPresenter<T extends IEnumTab> extends TabPresenter {

    void getRemoteMovementsData(T data);

    void updateBalance();

}
