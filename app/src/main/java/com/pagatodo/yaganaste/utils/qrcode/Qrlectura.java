package com.pagatodo.yaganaste.utils.qrcode;

public class Qrlectura {
    public  Auxl Aux ;
    public String Typ ;

    public Qrlectura(Auxl aux, String typ) {
        this.Aux = aux;
        Typ = typ;
    }

    public Auxl getAux() {
        return Aux;
    }

    public void setAux(Auxl aux) {
        this.Aux = aux;
    }

    public String getTyp() {
        return Typ;
    }

    public void setTyp(String typ) {
        Typ = typ;
    }
}
