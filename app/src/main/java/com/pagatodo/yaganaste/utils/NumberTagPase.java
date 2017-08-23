package com.pagatodo.yaganaste.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import static com.pagatodo.yaganaste.utils.StringConstants.SPACE;

/**
 * Created by Armando Sandoval on 07/08/2017.
 */

public class NumberTagPase implements TextWatcher {

    protected EditText editText;
    private int lengthAnterior;

    public NumberTagPase(EditText e) {
        editText = e;
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
        String response = StringUtils.genericFormat(s.toString().replaceAll(" ", ""), SPACE);
        editText.setText(response);
        editText.setSelection(response.length());
        lengthAnterior = s.length();
        editText.addTextChangedListener(this);
    }
}
