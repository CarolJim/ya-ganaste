package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by icruz on 07/11/2017.
 */

public class ObtenerCobrosMensualesRequest implements Serializable {

    private String IdTipoRegimenFiscal;

    public ObtenerCobrosMensualesRequest() {
    }

    public String getIdTipoRegimenFiscal() {
        return IdTipoRegimenFiscal;
    }

    public void setIdTipoRegimenFiscal(String idTipoRegimenFiscal) {
        this.IdTipoRegimenFiscal = idTipoRegimenFiscal;
    }
}
