package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;

/**
 * Created by asandovals on 20/04/2018.
 */

public class images implements Serializable {

    String chica ="";
    String grande ="";
    String mediana ="";

    public String getChica() {
        return chica;
    }

    public void setChica(String chica) {
        this.chica = chica;
    }

    public String getGrande() {
        return grande;
    }

    public void setGrande(String grande) {
        this.grande = grande;
    }

    public String getMediana() {
        return mediana;
    }

    public void setMediana(String mediana) {
        this.mediana = mediana;
    }
}
