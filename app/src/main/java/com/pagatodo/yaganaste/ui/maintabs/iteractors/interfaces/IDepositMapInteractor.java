package com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces;

import android.location.Location;

import com.pagatodo.yaganaste.exceptions.OfflineException;

/**
 * Created by Jordan on 23/05/2017.
 */

public interface IDepositMapInteractor {
    void getSucursalesWS(Location location) throws OfflineException;
}
