package com.pagatodo.yaganaste.utils;

import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * @author Gorro
 */
public class Validations {

    private final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}");
    private final static Pattern RFC_PATTERN_MORAL = Pattern.compile("[A-Z]{3}[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])[A-Z]{1}[0-9A-Z]{1}[0-9]{1}$");
    private final static Pattern RFC_PATTERN_FISICO = Pattern.compile("[A-Z]{4}[0-9]{6}[A-Z0-9]{3}$");

    private final static Pattern RFC_WITHOUT_HOMOCLAVE = Pattern.compile("^[A-Z{4}]+([0-9]{2})(?:0[1-9]|1[0-2])(?:0[1-9]|1[\\d]|2[\\d]|3[0-1])$");
    private final static Pattern RFC_HOMOCLAVE = Pattern.compile("^[A-Z0-9]{2}(?:[\\d]|A){1}$");


    public static boolean isMail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    public static boolean isMail(EditText email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email.getText().toString()).matches();
    }

    public static boolean isRFCFISICO(String email) {
        return RFC_PATTERN_FISICO.matcher(email).matches();
    }

    public static boolean isRFCFISICO(EditText email) {
        return RFC_PATTERN_FISICO.matcher(email.getText().toString()).matches();
    }

    public static boolean isRFCMORAL(String email) {
        return RFC_PATTERN_MORAL.matcher(email).matches();
    }

    public static boolean isRFCMORAL(EditText email) {
        return RFC_PATTERN_MORAL.matcher(email.getText().toString()).matches();
    }

    public static boolean isRFC(String rfc) {
        return RFC_WITHOUT_HOMOCLAVE.matcher(rfc).matches();
    }

    public static boolean isHomoclave(String rfc) {
        return RFC_HOMOCLAVE.matcher(rfc).matches();
    }

}
