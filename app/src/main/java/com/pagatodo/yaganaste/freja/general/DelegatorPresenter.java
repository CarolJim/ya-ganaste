package com.pagatodo.yaganaste.freja.general;

import com.pagatodo.yaganaste.freja.change.presenters.ChangeNipPresenterImp;
import com.pagatodo.yaganaste.freja.reset.presenters.ResetPinPresenter;
import com.pagatodo.yaganaste.freja.reset.presenters.ResetPinPresenterImp;
import com.pagatodo.yaganaste.ui.account.AprovPresenter;

/**
 * Presentador que delega las tareas de cambio de nip, aprovisionamiento y reset de NIO
 * @author Juan Guerra on 22/08/2017.
 */

public class DelegatorPresenter {
    private ResetPinPresenter resetPinPresenter;
    private ChangeNipPresenterImp changeNipPresenterImp;
    private AprovPresenter aprovPresenter;


    public DelegatorPresenter() {
        resetPinPresenter = new ResetPinPresenterImp(false);
        changeNipPresenterImp = new ChangeNipPresenterImp();

    }
}
