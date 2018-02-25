package com.pagatodo.yaganaste.freja.reset.presenters;

import com.pagatodo.yaganaste.freja.reset.managers.IResetNIPView;
import com.pagatodo.yaganaste.interfaces.View;

/**
 * @author Juan Guerra on 31/03/2017.
 */

public interface ResetPinPresenter {
    void doReseting(String newNip);

    void setResetNIPView(IResetNIPView resetNIPView);

}
