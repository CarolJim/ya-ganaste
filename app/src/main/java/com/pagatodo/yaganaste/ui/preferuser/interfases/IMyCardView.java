package com.pagatodo.yaganaste.ui.preferuser.interfases;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ActualizarDatosCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.BloquearCuentaResponse;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.EstatusCuentaResponse;

/**
 * Created by Francisco Manzo on 22/05/2017.
 */

public interface IMyCardView extends IPreferUserGeneric {
    void sendSuccessDatosCuentaToView(ActualizarDatosCuentaResponse response);

    void showLoader(String s);

    void hideLoader();

    void sendErrorDatosCuentaToView(String mensaje);

    void sendSuccessBloquearCuentaToView(BloquearCuentaResponse response);

    void sendErrorBloquearCuentaToView(String mensaje);
}
