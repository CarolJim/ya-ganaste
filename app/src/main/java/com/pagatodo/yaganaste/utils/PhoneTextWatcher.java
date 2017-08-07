package com.pagatodo.yaganaste.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import static com.pagatodo.yaganaste.utils.StringConstants.SPACE;

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
        String response = StringUtils.format(s.toString().replaceAll(" ", ""), SPACE, 2,4,4);
        editText.setText(response);
        editText.setSelection(response.length());
        lengthAnterior = s.length();
        editText.addTextChangedListener(this);
    }
}
