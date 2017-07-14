package com.pagatodo.yaganaste.ui.maintabs.managers;

import android.support.v4.widget.SwipeRefreshLayout;

import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataLocalizaSucursal;

import java.util.List;

/**
 * Created by Jordan on 23/05/2017.
 */

public interface DepositMapManager extends SwipeRefreshLayout.OnRefreshListener {
    void printSucursales(List<DataLocalizaSucursal> sucursalList);

    void setOnSucursalesNull();

    void onServiceError(DataSourceResult rescponse);
}
