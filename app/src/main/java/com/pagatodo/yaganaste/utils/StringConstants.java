package com.pagatodo.yaganaste.utils;

import com.pagatodo.yaganaste.exceptions.IllegalCallException;

/**
 * @author Juan Guerra on 19/04/2017.
 */

public class StringConstants {

    public static final String SPACE = " ";
    public static final String HAS_SESSION = "HAS_SESSION";
    public static final String NAME_USER = "NAME_USER";
    public static final String FULL_NAME_USER = "FULL_NAME_USER";
    public static final String CARD_NUMBER = "CARD_NUMBER";
    public static final String USER_BALANCE = "USER_BALANCE";
    public static final String UPDATE_DATE = "UPDATE_DATE";
    public static final String UPDATE_DATE_BALANCE_ADQ = "UPDATE_DATE_BALANCE_ADQ";
    public static final String ID_CUENTA = "ID_CUENTA";
    public static final String ADQUIRENTE_BALANCE = "ADQUIRENTE_BALANCE";
    public static final String CATALOG_VERSION = "CATALOG_VERSION";
    public static final String SIMPLE_NAME = "SIMPLE_NAME";

    public static final String HAS_PROVISIONING = "HAS_PROVISIONING";
    public static final String USER_PROVISIONED = "USER_PROVISIONED";
    public static final String HAS_PUSH = "HAS_PUSH";


    public static final String HAS_TOKEN_ONLINE = "HAS_TOKEN_ONLINE";

    private StringConstants() {
        throw new IllegalCallException("You can not create an instance for ViewPagerDataFactory");
    }
}
