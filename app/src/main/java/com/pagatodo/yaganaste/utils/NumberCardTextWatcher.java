package com.pagatodo.yaganaste.utils;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

import com.pagatodo.yaganaste.interfaces.ITextChangeListener;

import static com.pagatodo.yaganaste.utils.Recursos.SPACE;

/**
 * Created by Francisco Manzo on 17/03/2017.
 * Encargda de realizar el formato de CreditCard con los espacios visuales
 */

public class NumberCardTextWatcher implements TextWatcher {

    private EditText cardNumber;
    private ITextChangeListener listener;
    private int maxLength;
    private String finalText = "";
    private String auxText = "";

    public NumberCardTextWatcher(EditText cardNumber, int maxLength) {
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
        if (auxText.length() > maxLength - 3) {
            // Procesamos el recorte
            int baseNum = maxLength - 3;
            newString = charSequence.toString().substring(0, baseNum);
            auxText = StringUtils.genericFormat(newString.toString().replaceAll(" ", ""), SPACE);
            cardNumber.removeTextChangedListener(this);
            cardNumber.setText(auxText);
            cardNumber.addTextChangedListener(this);
            finalText = auxText;
        }
    }

    /**
     * Procesa el formato de poner espacios cuando se registran los numetros
     * 4, 9 y 14. Y cuando se hace un borrado, se registra con esos mismos nuemros, borando el
     * espacio mas el numero que logicamente corresponde
     *
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {
        cardNumber.removeTextChangedListener(this);
        String newS;
        // newS = StringUtils.format(s.toString().replace(" ",""), SPACE, 4,4,4,4);

        if (finalText.length() > 0) {
            newS = StringUtils.format(finalText.toString().replace(" ", ""), SPACE, 4, 4, 4, 4);
        } else {
            newS = StringUtils.format(s.toString().replace(" ", ""), SPACE, 4, 4, 4, 4);
        }

        cardNumber.setText(newS);
        cardNumber.setSelection(newS.length());
        cardNumber.addTextChangedListener(this);


        if (listener != null) {
            listener.onTextChanged();
            if (cardNumber.getText().toString().length() == maxLength) {
                listener.onTextComplete();
            }
        }
    }
}