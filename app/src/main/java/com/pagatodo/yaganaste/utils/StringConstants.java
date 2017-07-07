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
    public static final String USER_BALANCE = "USER_BALANCE";
    public static final String UPDATE_DATE = "UPDATE_DATE";
    public static final String ID_CUENTA = "ID_CUENTA";
    public static final String ADQUIRENTE_BALANCE = "ADQUIRENTE_BALANCE";

    public static final String CATALOG_VERSION = "CATALOG_VERSION";
}
