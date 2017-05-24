package com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces;

import android.location.Location;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataLocalizaSucursal;
import com.pagatodo.yaganaste.exceptions.OfflineException;

import java.util.List;

/**
 * Created by Jordan on 23/05/2017.
 */

public interface IDepositMapPresenter {
    void getSucursales(Location location) throws OfflineException;

    void onGetSucursalesSuccess(List<DataLocalizaSucursal> sucursalList);

    void onGetSucursalesFail(DataSourceResult rescponse);
}
