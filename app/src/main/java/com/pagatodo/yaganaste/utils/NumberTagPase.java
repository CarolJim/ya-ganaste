package com.pagatodo.yaganaste.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;

import static com.pagatodo.yaganaste.utils.StringConstants.SPACE;

/**
 * Created by Armando Sandoval on 07/08/2017.
 */

public class NumberTagPase implements TextWatcher {

    protected EditText editText;
    private int maxLength;
    private String finalText = "";
    private String auxText = "";

    public NumberTagPase(EditText e, int maxLength) {
        editText = e;
        this.maxLength = maxLength;
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        finalText = "";
        auxText = "";
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        auxText = charSequence.toString().replaceAll(" ", "");

        if (auxText.length() > 13) {
            editText.removeTextChangedListener(this);
            String subString = String.valueOf(charSequence.subSequence(0, 13));
            editText.setText(subString);
            editText.addTextChangedListener(this);
            finalText = subString;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        editText.removeTextChangedListener(this);
        String text = s.toString();
        String response = StringUtils.genericFormat(text.replaceAll(" ", ""), SPACE);
       // String response = StringUtils.format(s.toString().replaceAll(" ", ""), SPACE, 4, 4, 4,1);

        if (finalText.length() > 0) {
            response = StringUtils.format(finalText.toString().replaceAll(" ", ""), SPACE, 4,8,1);

        } else {
            response = StringUtils.format(s.toString().replaceAll(" ", ""), SPACE, 4,8,1);
        }

        editText.setText(response);
        editText.setSelection(Math.min(maxLength, editText.getText().length()));
        editText.addTextChangedListener(this);

   /*
       Codigo original, eliminar en las ultimas pruebas
    if (response.length() == 16) {

            //response = StringUtils.format(s.toString().replaceAll(" ", ""), SPACE, 4, 4, 4,1);
            editText.setText(response);
        } else {
            if (response.length() > maxLength) {
                editText.setText(response.substring(response.length() - maxLength, response.length()));
            } else {

            }
        }

        editText.setText(response);
        editText.setSelection(Math.min(maxLength, editText.getText().length()));
        editText.addTextChangedListener(this);

        */


    }
}
