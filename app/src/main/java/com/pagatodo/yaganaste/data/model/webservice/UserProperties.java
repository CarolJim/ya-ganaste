package com.pagatodo.yaganaste.data.model.webservice;

import java.io.Serializable;

/**
 * Created by jvazquez on 14/02/2017.
 */

public class UserProperties implements Serializable {
    public String idUsuario;
    public String tokenUsuario;

    public UserProperties() {
        idUsuario = "";
        tokenUsuario = "";
    }
}
