package com.pagatodo.yaganaste.data.model.webservice.response.manager;

import com.pagatodo.yaganaste.data.model.webservice.Respuesta;

import java.io.Serializable;

/**
 * Created by jvazquez on 14/02/2017.
 */

public class GenericResponse implements Serializable {
    public Respuesta respuesta;

    public GenericResponse(){
        respuesta = new Respuesta();
    }
}
