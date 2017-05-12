package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;

import java.util.List;

/**
 * Created by flima on 22/03/2017.
 */

public interface IAccountAddressRegisterView<E> extends INavigationView<Object, E> {

    public void setNeighborhoodsAvaliables(List<ColoniasResponse> listaColonias);
    public void setCurrentAddress(DataObtenerDomicilio domicilio);
}
