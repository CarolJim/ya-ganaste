package com.pagatodo.yaganaste.utils;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by flima on 09/03/2017.
 */

public class NumberTextWatcher implements TextWatcher {

    private final EditText edtText;
    private String textBefore;
    private boolean enableWriting;
    public boolean deleteText = false;

    public NumberTextWatcher(EditText editText) {
        this.edtText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        this.textBefore = s.toString();
        if (s.toString().length() > 0 && !deleteText) {
            String[] decimals = s.toString().split("\\.");
            if (decimals.length > 1 && decimals[1].length() == 2) {
                enableWriting = false;
            } else {
                enableWriting = true;
            }
        } else {
            enableWriting = true;
            deleteText = false;
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Double value = StringUtils.getDoubleValue(s.toString());
        edtText.removeTextChangedListener(this);
        if (value <= 999999.99 && enableWriting) {
            int thousands = value.intValue() / 1000;
            int lengthThousand = new String(thousands > 0 ? thousands + "" : "").length();
            if (lengthThousand > 0) {
                String newValue = s.toString().replaceAll(",", "");
                StringBuilder string = new StringBuilder(newValue);
                string.insert(newValue.contains("$") ? lengthThousand + 1 : lengthThousand, ",");
                edtText.setText(!newValue.contains("$") ? "$".concat(string.toString()) : string.toString());
            } else {
                String newValue = s.toString().replaceAll(",", "");
                edtText.setText(!newValue.contains("$") ? "$".concat(newValue) : newValue);
            }
            Selection.setSelection(edtText.getText(), edtText.getText().length());
        } else {
            edtText.setText(textBefore);
            Selection.setSelection(edtText.getText(), textBefore.length());
        }
        edtText.addTextChangedListener(NumberTextWatcher.this);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}