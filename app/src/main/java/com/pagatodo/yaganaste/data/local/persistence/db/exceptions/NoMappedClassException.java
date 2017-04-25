package com.pagatodo.yaganaste.data.local.persistence.db.exceptions;

/**
 * Created by jguerras on 17/01/2017.
 */

public class NoMappedClassException extends RuntimeException {

    public NoMappedClassException(String table) {
        super("No se ha Definido Ninguna Clase Para el Mapeo de la tabla " + table);
    }
}
