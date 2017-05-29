package com.pagatodo.yaganaste.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Jordan on 26/05/2017.
 */

public class PhoneTextWatcher implements TextWatcher {

    protected EditText editText;
    private int lengthAnterior;

    public PhoneTextWatcher(EditText e) {
        editText = e;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        editText.removeTextChangedListener(this);
        if (lengthAnterior < s.length()) {
            try {

                if (s.length() > 0) {
                    String number = s.toString();
                    number = number.replaceAll(" ", "");
                    String response = "";

                    for (int i = 0; i < number.length(); i++) {
                        response = response + number.charAt(i);
                        if (i == 1 || i == 5) {
                            response = response + " ";
                        }
                    }

                    editText.setText(response);
                    //editText.append(response);
                    editText.setSelection(response.length());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        lengthAnterior = s.length();
        editText.addTextChangedListener(this);
    }
}
