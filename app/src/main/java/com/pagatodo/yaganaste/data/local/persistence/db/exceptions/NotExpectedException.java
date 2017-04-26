package com.pagatodo.yaganaste.data.local.persistence.db.exceptions;

/**
 * Created by jguerras on 17/01/2017.
 */

public class NotExpectedException extends RuntimeException {

    public NotExpectedException(Throwable cause) {
        super("Ocurrió una Excepción no esperada", cause);
    }
}
