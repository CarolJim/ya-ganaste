package com.pagatodo.yaganaste.ui.maintabs.presenters;

import com.pagatodo.yaganaste.interfaces.IEnumTab;
import com.pagatodo.yaganaste.ui.maintabs.managers.MovementsManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.MovementsPresenter;

/**
 * @author Juan Guerra on 28/03/2017.
 */

public class MovementsPresenterImp<T extends IEnumTab> implements MovementsPresenter<T>, MovementsManager {



    @Override
    public void getRemoteMovementsData(T data) {

    }



    @Override
    public void onSuccesResponse(Object response) {

    }

    @Override
    public void onFailed(int errorCode, int action, String error) {

    }
}
