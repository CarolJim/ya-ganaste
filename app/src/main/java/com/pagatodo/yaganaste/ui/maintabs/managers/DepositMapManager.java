package com.pagatodo.yaganaste.ui.maintabs.managers;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataLocalizaSucursal;

import java.util.List;

/**
 * Created by Jordan on 23/05/2017.
 */

public interface DepositMapManager {
    void printSucursales(List<DataLocalizaSucursal> sucursalList);
}
