package com.pagatodo.yaganaste.ui.preferuser.presenters;

import com.pagatodo.yaganaste.ui._controllers.PreferUserActivity;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserIteractor;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserPresenter;
import com.pagatodo.yaganaste.ui.preferuser.interfases.IPreferUserView;
import com.pagatodo.yaganaste.ui.preferuser.iteractors.PreferUserIteractor;

/**
 * Created by Francisco Manzo on 08/06/2017.
 * Presenter general para la actividad PreferUserActivity
 * Para gestionar todos los eventos de los fragmentos
 */

public class PreferUserPresenter implements IPreferUserPresenter{

    PreferUserActivity mView;
    IPreferUserIteractor iPreferUserIteractor;

    public PreferUserPresenter(PreferUserActivity mView) {
        this.mView = mView;

        iPreferUserIteractor = new PreferUserIteractor(this);
    }

    @Override
    public void testToast() {
        iPreferUserIteractor.testToastIteractor();
    }

    @Override
    public void testToastSucess() {

    }
}
