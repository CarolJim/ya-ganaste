package com.pagatodo.yaganaste.ui.cupo.presenters;

import com.pagatodo.yaganaste.ui.cupo.interactores.CuentanosMasInteractor;
import com.pagatodo.yaganaste.ui.cupo.interactores.interfaces.ICuentanosMasInteractor;
import com.pagatodo.yaganaste.ui.cupo.presenters.interfaces.ICuentanosMasPresenter;

/**
 * Created by Jordan on 26/07/2017.
 */

public class CuentanosMasPresenter implements ICuentanosMasPresenter {
    private ICuentanosMasInteractor cuentanosMasInteractor;

    public CuentanosMasPresenter() {
        cuentanosMasInteractor = new CuentanosMasInteractor();
    }
}
