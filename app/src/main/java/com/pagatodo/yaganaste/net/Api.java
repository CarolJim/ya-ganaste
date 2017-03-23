package com.pagatodo.yaganaste.net;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by flima on 22/03/2017.
 */

public class Api {

    public static final Map<String, String> headersYaGanaste;
    static
    {
        headersYaGanaste = new HashMap<>();
        headersYaGanaste.put(RequestHeaders.IdDispositivo, "2");
        headersYaGanaste.put(RequestHeaders.NombreUsuario, RequestHeaders.getUsername());
        headersYaGanaste.put(RequestHeaders.IdComponente, "1");
    }

    public static final Map<String, String> headersAdq;
    static
    {
        headersAdq = new HashMap<String, String>();
        headersAdq.put("Version", "1.0.7");
        headersAdq.put("SO", "Android");
    }


}
