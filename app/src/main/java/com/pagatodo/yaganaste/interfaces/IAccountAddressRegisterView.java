package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerDocumentosResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ObtenerDomicilioResponse;

import java.util.List;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAccountAddressRegisterView extends INavigationView {

    public void setNeighborhoodsAvaliables(List<ColoniasResponse> listaColonias);
    public void setCurrentAddress(ObtenerDomicilioResponse domicilio);
}
