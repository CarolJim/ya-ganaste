package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

/**
 * Created by Francisco Manzo on 21/03/2017.
 * Encargada de crear el objeto de respuesta del proceso de registro de Token
 */

public class RegisterFBTokenResponse extends GenericResponse {

    private DataRegisterFBToken Respuesta;

    public RegisterFBTokenResponse() {
        Respuesta = new DataRegisterFBToken();
    }
}
