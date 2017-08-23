package com.pagatodo.yaganaste.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.pagatodo.yaganaste.interfaces.ITextChangeListener;

import static com.pagatodo.yaganaste.utils.StringConstants.SPACE;

/**
 * Created by Jordan on 26/05/2017.
 */

public class PhoneTextWatcher implements TextWatcher {

    protected EditText editText;
    private ITextChangeListener listener;

    public PhoneTextWatcher(EditText e) {
        editText = e;
    }

    public void setOnITextChangeListener(ITextChangeListener listener) {
        this.listener = listener;
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
        String response = StringUtils.format(s.toString().replaceAll(" ", ""), SPACE, 2, 4, 4);
        editText.setText(response);
        editText.setSelection(response.length());
        editText.addTextChangedListener(this);

        if (listener != null) {
            listener.onTextChanged();
            if (response.length() == 12) {
                listener.onTextComplete();
            }
        }
    }
}
