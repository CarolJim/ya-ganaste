package com.pagatodo.yaganaste.data.model.webservice;

import java.io.Serializable;

/**
 * Created by jvazquez on 14/02/2017.
 */

public class Respuesta implements Serializable {
    public String codigoRespuesta;
    public String mensaje;

    public Respuesta(){
        codigoRespuesta = "";
        mensaje         = "";
    }
}
