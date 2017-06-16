package com.pagatodo.yaganaste.utils;

import com.pagatodo.yaganaste.exceptions.IllegalCallException;

/**
 * @author Juan Guerra on 19/04/2017.
 */

public class StringConstants {

    private StringConstants() {
        throw new IllegalCallException("You can not create an instance for ViewPagerDataFactory");
    }

    public static final String SPACE = " ";

    public static final String HAS_SESSION = "HAS_SESSION";
    public static final String NAME_USER = "NAME_USER";
    public static final String FULL_NAME_USER = "FULL_NAME_USER";
    public static final String CARD_NUMBER = "CARD_NUMBER";
}
