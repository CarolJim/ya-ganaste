package com.pagatodo.yaganaste.ui.maintabs.presenters;

import android.location.Location;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataLocalizaSucursal;
import com.pagatodo.yaganaste.exceptions.OfflineException;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.DepositMapInteractor;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IDepositMapInteractor;
import com.pagatodo.yaganaste.ui.maintabs.managers.DepositMapManager;
import com.pagatodo.yaganaste.ui.maintabs.presenters.interfaces.IDepositMapPresenter;

import java.util.List;

/**
 * Created by Jordan on 23/05/2017.
 */

public class DepositMapPresenter implements IDepositMapPresenter {
    IDepositMapInteractor depositMapInteractor;
    DepositMapManager mapManager;

    public DepositMapPresenter(DepositMapManager manager) {
        this.depositMapInteractor = new DepositMapInteractor(this);
        mapManager = manager;
    }


    @Override
    public void getSucursales(Location location) throws OfflineException {
        this.depositMapInteractor.getSucursalesWS(location);
    }

    @Override
    public void onGetSucursalesSuccess(List<DataLocalizaSucursal> sucursalList) {
        mapManager.printSucursales(sucursalList);
    }

    @Override
    public void onGetSucursalesFail(DataSourceResult rescponse) {

    }


}
