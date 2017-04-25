package com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerCatalogosResponse;

import java.util.List;

/**
 * Created by Jordan on 11/04/2017.
 */

public interface IPaymentsCarouselIteractor {
    void getCatalogosFromService();
    void getCatalogosFromDB(int tabID);
}
