package com.pagatodo.yaganaste.utils;

import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Created by flima on 21/02/2017.
 */

public class ValidateForm {

    public static boolean isEditTextsFill(EditText... edts){

        for(EditText edt : edts)
            if(edt.getText().toString().isEmpty())
                return false;

        return true;
    }

    public static String isEditTextEmpty(EditText edt,String message){

            if(edt.getText().toString().isEmpty())
                return message;

        return "";
    }


    private  final static Pattern emailPattern = Pattern.compile(
            "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$" );

    public static boolean isValidEmailAddress(String email){
        return emailPattern.matcher(email).matches();
    }
}
