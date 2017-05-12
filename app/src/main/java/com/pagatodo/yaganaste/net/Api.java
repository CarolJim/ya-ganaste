package com.pagatodo.yaganaste.net;

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
        headersYaGanaste.put(RequestHeaders.NombreUsuario, RequestHeaders.getUsername());// TODO: 04/05/2017
        headersYaGanaste.put(RequestHeaders.IdComponente, "1");
        return headersYaGanaste;
    }

    public static Map<String, String> getHeadersAdq(){
        Map<String, String> headersAdq = new HashMap<>();
        headersAdq = new HashMap<String, String>();
        headersAdq.put("Version", "1.0.7");
        headersAdq.put("SO", "Android");
        return headersAdq;
    }

}
