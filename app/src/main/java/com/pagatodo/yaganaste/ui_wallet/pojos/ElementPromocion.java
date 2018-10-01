package com.pagatodo.yaganaste.ui_wallet.pojos;

public class ElementPromocion {

    String titlo,vigencia,url_marca,status;
    boolean casa;

    public ElementPromocion(String titlo, String vigencia, String url_marca, String status, boolean casa) {
        this.titlo = titlo;
        this.vigencia = vigencia;
        this.url_marca = url_marca;
        this.status = status;
        this.casa = casa;
    }

    public String getTitlo() {
        return titlo;
    }

    public void setTitlo(String titlo) {
        this.titlo = titlo;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public String getUrl_marca() {
        return url_marca;
    }

    public void setUrl_marca(String url_marca) {
        this.url_marca = url_marca;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCasa() {
        return casa;
    }

    public void setCasa(boolean casa) {
        this.casa = casa;
    }
}
