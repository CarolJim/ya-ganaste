package com.pagatodo.yaganaste.data.model.webservice.request.starbucks;

import com.google.gson.annotations.SerializedName;
import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CuestionarioEntity;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataObtenerDomicilio;

import java.io.Serializable;
import java.util.List;

/**
 * Created by asandovals on 19/04/2018.
 */

public class LoginStarbucksRequest implements Serializable {

    @SerializedName("email")
    private String email;

    @SerializedName("contrasenia")
    private String contrasenia;

    @SerializedName("dispositivo")
    private DispositivoStartBucks dispositivoStartBucks;

    @SerializedName("fuente")
    private String fuente;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public DispositivoStartBucks getDispositivoStartBucks() {
        return dispositivoStartBucks;
    }

    public void setDispositivoStartBucks(DispositivoStartBucks dispositivoStartBucks) {
        this.dispositivoStartBucks = dispositivoStartBucks;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }
}
