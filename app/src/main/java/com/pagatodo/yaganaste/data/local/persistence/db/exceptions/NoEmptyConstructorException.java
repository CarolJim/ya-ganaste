package com.pagatodo.yaganaste.data.local.persistence.db.exceptions;

/**
 * Created by jguerras on 17/01/2017.
 */

public class NoEmptyConstructorException extends RuntimeException {

    public NoEmptyConstructorException(String message) {
        super(message);
    }

    public NoEmptyConstructorException(String message, Throwable cause) {
        super(message, cause);
    }
}
