package com.pagatodo.yaganaste.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.pagatodo.yaganaste.interfaces.ITextChangeListener;

import static com.pagatodo.yaganaste.utils.StringConstants.SPACE;

/**
 * Created by Francisco Manzo on 17/03/2017.
 * Encargda de realizar el formato de CLABE con los espacios visuales
 */

public class NumberClabeTextWatcher implements TextWatcher {

    private EditText cardNumber;
    private ITextChangeListener listener;

    public NumberClabeTextWatcher(EditText cardNumber) {
        this.cardNumber = cardNumber;
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

    /**
     * Procesa el formato de poner espacios cuando se registran los numetros
     * 4, 8, 13 y 18. Cuando se hace un borrado, se registra con esos mismos nuemros, borando el
     * espacio mas el numero que logicamente corresponde
     *
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {
        cardNumber.removeTextChangedListener(this);
        String response = StringUtils.format(s.toString().replaceAll(" ", ""), SPACE, 3, 3, 4, 4, 4);
        cardNumber.setText(response);
        cardNumber.setSelection(response.length());
        cardNumber.addTextChangedListener(this);

        if (listener != null) {
            listener.onTextChanged();
            if (response.length() == 22) {
                listener.onTextComplete();
            }
        }
    }
}