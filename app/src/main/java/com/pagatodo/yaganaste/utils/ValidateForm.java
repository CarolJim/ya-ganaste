package com.pagatodo.yaganaste.utils;

import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Created by flima on 21/02/2017.
 */

public class ValidateForm {

    public static boolean isEditTextsFill(EditText... edts) {

        for (EditText edt : edts)
            if (edt.getText().toString().isEmpty())
                return false;

        return true;
    }

    public static String isEditTextEmpty(EditText edt, String message) {

        if (edt.getText().toString().isEmpty())
            return message;

        return "";
    }


    private final static Pattern emailPattern = Pattern.compile(
            "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");

    public static boolean isValidEmailAddress(String email) {
        return emailPattern.matcher(email).matches();
    }

    private final static Pattern passwprdPattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})");
    /*
    *   (			# Start of group
        (?=.*\d)		#   must contains one digit from 0-9
        (?=.*[a-z])		#   must contains one lowercase characters
        (?=.*[A-Z])		#   must contains one uppercase characters
              .		    #     match anything with previous condition checking
                {6,20}	#        length at least 6 characters and maximum of 20
            )			# End of group
    * */

    public static boolean isValidPassword(String pass) {
        return passwprdPattern.matcher(pass).matches();
    }

    private final static Pattern phonePattern = Pattern.compile("d{7}");

    public static boolean isValidPhone(String phone) {
        return phonePattern.matcher(phone).matches();
    }

    private final static Pattern cellPhonePattern = Pattern.compile("d{10}");

    public static boolean isValidCellPhone(String cellPhone){
        return  cellPhonePattern.matcher(cellPhone).matches();
    }

    private final static Pattern zipCodePattern = Pattern.compile("^[0-9]{5}$");

    /*
            ^           # Assert position at the beginning of the string.
            [0-9]{5}    # Match a digit, exactly five times.
            (?:         # Group but don't capture:
              -         #   Match a literal "-".
              [0-9]{4}  #   Match a digit, exactly four times.
            )           # End the non-capturing group.
              ?         #   Make the group optional.
            $           # Assert position at the end of the string.
     */
    public static boolean isValidZipCode(String zipCode) {
        return zipCodePattern.matcher(zipCode).matches();
    }

}
