package com.pagatodo.yaganaste.modules.management.response;

import java.io.Serializable;

public class OnlyQrResponse implements Serializable {

    private CAuxResponse Aux;
    private Optresponse Opt;
    private String Typ;
    private String Ver;

    public OnlyQrResponse(CAuxResponse aux, Optresponse opt, String typ, String ver) {
        Aux = aux;
        Opt = opt;
        Typ = typ;
        Ver = ver;
    }

    public OnlyQrResponse() {
    }

    public CAuxResponse getAux() {
        return Aux;
    }

    public void setAux(CAuxResponse aux) {
        Aux = aux;
    }

    public Optresponse getOpt() {
        return Opt;
    }

    public void setOpt(Optresponse opt) {
        Opt = opt;
    }

    public String getTyp() {
        return Typ;
    }

    public void setTyp(String typ) {
        Typ = typ;
    }

    public String getVer() {
        return Ver;
    }

    public void setVer(String ver) {
        Ver = ver;
    }
}
