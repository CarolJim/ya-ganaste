package com.pagatodo.yaganaste.interfaces;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ColoniasResponse;
import com.pagatodo.yaganaste.interfaces.enums.WebService;

import java.util.List;

/**
 * Created by asandovals on 26/04/2018.
 */

public interface IregisterCompleteStarbucks extends INavigationView {
    void registerstarsucced();
    void llenardatospersona();
    void registerfail(String mensaje);
    void llenarcolonias(WebService ws, List<ColoniasResponse> list);
    void loginstarsucced();
    void loginfail(String mensaje);

}
