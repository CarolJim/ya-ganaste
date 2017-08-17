package com.pagatodo.yaganaste.utils;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

import com.pagatodo.yaganaste.interfaces.ITextChangeListener;

/**
 * Created by Francisco Manzo on 17/03/2017.
 * Encargda de realizar el formato de CreditCard con los espacios visuales
 */

public class NumberCardTextWatcher implements TextWatcher {

    private String dataNumberNew;
    private String dataNumberOld;
    private EditText cardNumber;
    private ITextChangeListener listener;

    public NumberCardTextWatcher(EditText cardNumber) {
        this.cardNumber = cardNumber;
        dataNumberNew = "";
        dataNumberOld = "";
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
     * 4, 9 y 14. Y cuando se hace un borrado, se registra con esos mismos nuemros, borando el
     * espacio mas el numero que logicamente corresponde
     *
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {
        dataNumberNew = s.toString();
        if (dataNumberNew.length() > dataNumberOld.length()) {

            /**
             * Registra los espacios y posisciona el cursor al final del editext para continuar de
             * manera normal
             */
            if (dataNumberNew.length() == 4) {
                dataNumberNew = dataNumberNew + " ";
                cardNumber.setText(dataNumberNew.toString());
                Selection.setSelection(cardNumber.getText(), dataNumberNew.toString().length());

            } else if (dataNumberNew.length() == 9) {
                dataNumberNew = dataNumberNew + " ";
                cardNumber.setText(dataNumberNew.toString());
                Selection.setSelection(cardNumber.getText(), dataNumberNew.toString().length());
            } else if (dataNumberNew.length() == 14) {
                dataNumberNew = dataNumberNew + " ";
                cardNumber.setText(dataNumberNew.toString());
                Selection.setSelection(cardNumber.getText(), dataNumberNew.toString().length());
            }

            dataNumberOld = dataNumberNew;
        } else {
            /**
             * Proceso de brrado, elimina es caracter siguiente a la izquierda, despues de que se
             * borra de manera automatica el espacio al orpimir la flecha
             */
            if (dataNumberNew.length() == 14) {
                dataNumberNew = dataNumberNew.substring(0, dataNumberNew.length() - 1);
                cardNumber.setText(dataNumberNew.toString());
                Selection.setSelection(cardNumber.getText(), dataNumberNew.toString().length());

            } else if (dataNumberNew.length() == 9) {
                dataNumberNew = dataNumberNew.substring(0, dataNumberNew.length() - 1);
                cardNumber.setText(dataNumberNew.toString());
                Selection.setSelection(cardNumber.getText(), dataNumberNew.toString().length());
            } else if (dataNumberNew.length() == 4) {
                dataNumberNew = dataNumberNew.substring(0, dataNumberNew.length() - 1);
                cardNumber.setText(dataNumberNew.toString());
                Selection.setSelection(cardNumber.getText(), dataNumberNew.toString().length());
            }


            dataNumberOld = dataNumberNew;
        }
        if (listener != null) {
            listener.onTextChanged();
            if (dataNumberNew.length() == 19) {
                listener.onTextComplete();
            }
        }
    }
}