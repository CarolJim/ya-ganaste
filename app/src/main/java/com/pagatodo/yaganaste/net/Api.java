package com.pagatodo.yaganaste.net;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by flima on 22/03/2017.
 */

public class Api {

    public static Map<String, String> getHeadersYaGanaste() {
        Map<String, String> headersYaGanaste = new HashMap<>();
        //headersYaGanaste.put("Content-type", "application/json");
        headersYaGanaste.put(RequestHeaders.IdDispositivo, "2");
        String userName = RequestHeaders.getUsername().isEmpty() ?
                "correo@correo.correo" : RequestHeaders.getUsername();
        headersYaGanaste.put(RequestHeaders.NombreUsuario, userName);// TODO: 04/05/2017
        headersYaGanaste.put(RequestHeaders.IdComponente, "1");
        return headersYaGanaste;
    }

    public static Map<String, String> getHeadersAdq() {
        Map<String, String> headersAdq = new HashMap<>();
        headersAdq.put("Version", "1.0.7");
        headersAdq.put("SO", "Android");
        return headersAdq;
    }

    public static Map<String, String> getHeadersSb(){
        Map<String, String> headersSb = new HashMap<>();

        //headersSb.put(RequestHeaders.TokenSeguridad, RequestHeaders.getTokenSeguridad());
        //headersSb.put(RequestHeaders.numeroMiembro, RequestHeaders.getNumeroMiembro());
        headersSb.put(RequestHeaders.TokenSeguridad, "8b12d5f399263d74b09ede02364e6cd2c44b941696a996892630f847094c830f");
        headersSb.put(RequestHeaders.numeroMiembro, "6135294392810562");
        headersSb.put("fuente", "Movil");
        return  headersSb;
    }

}
