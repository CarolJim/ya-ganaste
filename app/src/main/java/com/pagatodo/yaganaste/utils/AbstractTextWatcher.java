package com.pagatodo.yaganaste.utils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * @author Juan Guerra on 04/05/2017.
 */

public abstract class AbstractTextWatcher implements TextWatcher {

    private boolean isEnabled;

    public AbstractTextWatcher() {
        this.isEnabled = true;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public final void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (isEnabled) {
            beforeTextChanged(s.toString(), start, count, after);
        }
    }

    @Override
    public final void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isEnabled) {
            onTextChanged(s.toString(), start, before, count);
        }
    }

    @Override
    public final void afterTextChanged(Editable s) {
        if (isEnabled) {
            afterTextChanged(s.toString());
        }
    }

    public void beforeTextChanged(String s, int start, int count, int after) {
        //Override to implement your own action
    }

    public void onTextChanged(String s, int start, int before, int count) {
        //Override to implement your own action
    }

    public void afterTextChanged(String s) {
        //Override to implement your own action
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

}
