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

    public NumberTagPase(EditText e, int maxLength) {
        editText = e;
        this.maxLength = maxLength;
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        editText.removeTextChangedListener(this);
        String text = s.toString();
        //String response = StringUtils.genericFormat(text.replaceAll(" ", ""), SPACE);
        String response = StringUtils.format(s.toString().replaceAll(" ", ""), SPACE, 4, 4, 4,1);

        if (response.length() == 16) {
            response = StringUtils.format(s.toString().replaceAll(" ", ""), SPACE, 4, 4, 4,1);
            editText.setText(response);
        } else {
            if (response.length() > maxLength) {
                editText.setText(response.substring(response.length() - maxLength, response.length()));
            } else {
                editText.setText(response);
            }
        }
            editText.setSelection(Math.min(maxLength, editText.getText().length()));
            editText.addTextChangedListener(this);

    }
}
