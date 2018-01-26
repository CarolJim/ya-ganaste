package com.pagatodo.yaganaste.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import static com.pagatodo.yaganaste.utils.StringConstants.SPACE;

/**
 * Created by FManzo on 25/01/2018.
 */

public class NumberReferenceTextWatcher implements TextWatcher {
    protected EditText editText;
    private int maxLength;
    private String finalText = "";
    private String auxText = "";

    public NumberReferenceTextWatcher(EditText e, int maxLength) {
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
        auxText = StringUtils.genericFormat(charSequence.toString().replaceAll(" ", ""), SPACE);

        if (auxText.length() > maxLength) {
            editText.removeTextChangedListener(this);
            String subString = String.valueOf(auxText.subSequence(0, maxLength));
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

        if (finalText.length() > 0) {
            response = StringUtils.genericFormat(finalText.toString().replaceAll(" ", ""), SPACE);

        } else {
            response = StringUtils.genericFormat(s.toString().replaceAll(" ", ""), SPACE);
        }

        editText.setText(response);
        editText.setSelection(Math.min(maxLength, editText.getText().length()));
        editText.addTextChangedListener(this);
    }
}
