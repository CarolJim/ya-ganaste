package com.pagatodo.yaganaste.ui.maintabs.iteractors;

import com.google.gson.Gson;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerCatalogosResponse;
import com.pagatodo.yaganaste.ui.maintabs.iteractors.interfaces.IPaymentsCarouselIteractor;
import com.pagatodo.yaganaste.utils.Utils;

/**
 * Created by Jordan on 11/04/2017.
 */

public class PaymentsCarouselIteractor implements IPaymentsCarouselIteractor {

    public PaymentsCarouselIteractor(){

    }

    @Override
    public ObtenerCatalogosResponse getCatalogos() {
        Gson gson = new Gson();
        String catalogosJSONStringResponse = Utils.getJSONStringFromAssets(App.getContext(), "files/catalogos.json");
        return gson.fromJson(catalogosJSONStringResponse, ObtenerCatalogosResponse.class);
    }

}
