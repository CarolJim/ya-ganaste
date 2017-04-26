package com.pagatodo.yaganaste.data.local.persistence.db.exceptions;

/**
 * Created by jguerras on 17/01/2017.
 */

public class AbstractEntityException extends RuntimeException {

    public AbstractEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbstractEntityException(String message) {
        super(message);
    }
}
