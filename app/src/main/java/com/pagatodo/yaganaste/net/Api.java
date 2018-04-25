package com.pagatodo.yaganaste.net;

import com.pagatodo.yaganaste.App;

import java.util.HashMap;
import java.util.Map;

import static com.pagatodo.yaganaste.utils.Recursos.MEMBER_NUMBER_STARBUCKS;
import static com.pagatodo.yaganaste.utils.Recursos.SECURITY_TOKEN_STARBUCKS;

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

    public static Map<String, String> getHeadersStarbucks() {
        Map<String, String> headersYaGanaste = new HashMap<>();
        headersYaGanaste.put("Content-type", "application/json");
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
        headersSb.put(RequestHeaders.TokenSeguridad, App.getInstance().getPrefs().loadData(SECURITY_TOKEN_STARBUCKS));
        headersSb.put(RequestHeaders.numeroMiembro, App.getInstance().getPrefs().loadData(MEMBER_NUMBER_STARBUCKS));
        //headersSb.put(RequestHeaders.TokenSeguridad, "11b1dfd55a80374100d5ca228ab55c18c3f4646c1d0c42f1511762b1c8e325a8");
        //headersSb.put(RequestHeaders.numeroMiembro, "6089031902208787");
        headersSb.put("fuente", "Movil");
        return  headersSb;
    }

}
