package com.pagatodo.yaganaste.net;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ActivacionServicioMovilRequest;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.ValidarEstatusUsuarioRequest;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ValidarEstatusUsuarioResponse;
import static com.pagatodo.yaganaste.interfaces.enums.HttpMethods.METHOD_POST;
import static com.pagatodo.yaganaste.interfaces.enums.WebService.VALIDAR_ESTATUS_USUARIO;
import static com.pagatodo.yaganaste.utils.Recursos.URL_SERVER_ADTVO;

/**
 * Created by flima on 17/03/2017.
 *
 * Clase para gestionar el WS de Ya Ganaste Administrativo.
 */

public class ApiAdtvo extends Api{


    /**
     * Método que se invoca cuando se desea validar si existe un Usuario en el FWS Externo de BPT y en caso de existir,
     * nos indique si es Cliente de BPT y si es Agente.
     *
     * @param request {@link ValidarEstatusUsuarioRequest} body de la petición.
     * @param result {@link IRequestResult} listener del resultado de la petición.
     * */
    public static void validarEstatusUsuario(ValidarEstatusUsuarioRequest request, IRequestResult result) {
        NetFacade.consumeWS(VALIDAR_ESTATUS_USUARIO,
                METHOD_POST, URL_SERVER_ADTVO + App.getContext().getString(R.string.validateStatusUserUrl),
                headersYaGanaste,request, ValidarEstatusUsuarioResponse.class,result);

    }

}

