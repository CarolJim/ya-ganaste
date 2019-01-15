package com.pagatodo.yaganaste.exceptions;

import androidx.annotation.NonNull;

/**
 * @author Juan Guerra on 24/03/2017.
 */

public class IllegalCallException extends RuntimeException {

    public IllegalCallException(@NonNull String message) {
        super(message);
    }
}
