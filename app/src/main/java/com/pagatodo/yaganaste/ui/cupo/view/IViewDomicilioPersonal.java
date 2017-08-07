package com.pagatodo.yaganaste.ui.cupo.view;


import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.interfaces.INavigationView;

import java.util.List;

/**
 * Created by Tato on 03/08/17.
 */

public interface IViewDomicilioPersonal<E> extends INavigationView<Object, E> {

    void setNeighborhoodsAvaliables(List<ColoniasResponse> listaColonias);

}
