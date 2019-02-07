package com.pagatodo.yaganaste.modules.management.request;

import java.io.Serializable;

public class CardRemplaceRequest implements Serializable {

    private String TarjetaAntigua;
    private String TarjetaNueva;

    public CardRemplaceRequest(String tarjetaAntigua, String tarjetaNueva) {
        TarjetaAntigua = tarjetaAntigua;
        TarjetaNueva = tarjetaNueva;
    }

    public String getTarjetaAntigua() {
        return TarjetaAntigua;
    }

    public void setTarjetaAntigua(String tarjetaAntigua) {
        TarjetaAntigua = tarjetaAntigua;
    }

    public String getTarjetaNueva() {
        return TarjetaNueva;
    }

    public void setTarjetaNueva(String tarjetaNueva) {
        TarjetaNueva = tarjetaNueva;
    }
}
