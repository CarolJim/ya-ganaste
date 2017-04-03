package com.pagatodo.yaganaste.exceptions;

import android.support.annotation.NonNull;

/**
 * @author Juan Guerra on 24/03/2017.
 */

public class IllegalFactoryParameterException extends RuntimeException {

    public IllegalFactoryParameterException(String parameter){
        super("Param " + parameter + " is not recognized" );
    }
}
