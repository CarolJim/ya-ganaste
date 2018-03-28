package com.pagatodo.yaganaste.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.pagatodo.yaganaste.interfaces.ITextChangeListener;

import static com.pagatodo.yaganaste.utils.Recursos.SPACE;

/**
 * Created by Francisco Manzo on 17/03/2017.
 * Encargda de realizar el formato de CLABE con los espacios visuales
 */

public class NumberClabeTextWatcher implements TextWatcher {

    private EditText cardNumber;
    private ITextChangeListener listener;
    private int maxLength;
    private String finalText = "";
    private String auxText = "";

    public NumberClabeTextWatcher(EditText cardNumber, int maxLength) {
        this.cardNumber = cardNumber;
        this.maxLength = maxLength;
    }

    public void setOnITextChangeListener(ITextChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        finalText = "";
        auxText = "";
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        String newString = "";
        auxText = charSequence.toString().replaceAll(" ", "");

        // Procesamos si el texto se sale de los rangos permitidos de maxlenght
        if (auxText.length() > maxLength - 4) {
            // Procesamos el recorte
            int baseNum = maxLength - 4;
            newString = charSequence.toString().substring(0, baseNum);
            auxText = StringUtils.format(newString.toString().replaceAll(" ", ""), SPACE, 3, 3, 4, 4, 4);
            cardNumber.removeTextChangedListener(this);
            cardNumber.setText(auxText);
            cardNumber.addTextChangedListener(this);
            finalText = auxText;
        }
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
        String newS;

        if (finalText.length() > 0) {
            newS = StringUtils.format(finalText.toString().replace(" ", ""), SPACE, 3, 3, 4, 4, 4);
        } else {
            newS = StringUtils.format(s.toString().replace(" ", ""), SPACE, 3, 3, 4, 4, 4);
        }

        //String response = StringUtils.format(s.toString().replaceAll(" ", ""), SPACE, 3, 3, 4, 4, 4);
        cardNumber.setText(newS);
        cardNumber.setSelection(newS.length());
        cardNumber.addTextChangedListener(this);

        if (listener != null) {
            listener.onTextChanged();
            if (newS.length() == 22) {
                listener.onTextComplete();
            }
        }
    }
}