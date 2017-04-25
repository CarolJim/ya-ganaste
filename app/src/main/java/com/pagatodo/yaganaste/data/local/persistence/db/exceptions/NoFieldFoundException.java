package com.pagatodo.yaganaste.data.local.persistence.db.exceptions;

/**
 * Created by jguerras on 17/01/2017.
 */

public class NoFieldFoundException extends RuntimeException {

    public NoFieldFoundException() {
        super("Las columnas de la tabla no coinciden con la entidad, debes " +
                "actualizar tu modelo de datos");
    }
}
