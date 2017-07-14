package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class AsignarContraseniaRequest implements Serializable {

    private String NombreUsuario = "";
    private String SemillaTemporal = "";
    private String ContrasenaNueva = "";


    public AsignarContraseniaRequest() {
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        NombreUsuario = nombreUsuario;
    }

    public String getSemillaTemporal() {
        return SemillaTemporal;
    }

    public void setSemillaTemporal(String semillaTemporal) {
        SemillaTemporal = semillaTemporal;
    }

    public String getContrasenaNueva() {
        return ContrasenaNueva;
    }

    public void setContrasenaNueva(String contrasenaNueva) {
        ContrasenaNueva = contrasenaNueva;
    }
}
