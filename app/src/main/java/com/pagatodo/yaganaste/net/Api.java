package com.pagatodo.yaganaste.net;

import com.pagatodo.yaganaste.data.model.webservice.request.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by flima on 22/03/2017.
 */

public class Api {

    public static Map<String, String> getHeadersYaGanaste(){
        Map<String, String> headersYaGanaste = new HashMap<>();
        //headersYaGanaste.put("Content-type", "application/json");
        headersYaGanaste.put(RequestHeaders.IdDispositivo, "2");
        String userName = RequestHeaders.getUsername().isEmpty() ?
                "correo@correo.correo" : RequestHeaders.getUsername();
        headersYaGanaste.put(RequestHeaders.NombreUsuario, userName);// TODO: 04/05/2017
        headersYaGanaste.put(RequestHeaders.IdComponente, "1");
        return headersYaGanaste;
    }

    public static Map<String, String> getHeadersAdq(){
        Map<String, String> headersAdq = new HashMap<>();
        headersAdq.put("Version", "1.0.7");
        headersAdq.put("SO", "Android");
        return headersAdq;
    }

}
