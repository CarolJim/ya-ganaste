package com.pagatodo.yaganaste.utils;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Francisco Manzo on 17/03/2017.
 * Encargda de realizar el formato de CLABE con los espacios visuales
 */

public class NumberClabeTextWatcher implements TextWatcher {

    String dataNumberNew;
    String dataNumberOld;
    EditText cardNumber;

    public NumberClabeTextWatcher(EditText cardNumber) {
        this.cardNumber = cardNumber;
        dataNumberNew = "";
        dataNumberOld = "";
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
            if (dataNumberNew.length() == 3) {
                dataNumberNew = dataNumberNew + " ";
                cardNumber.setText(dataNumberNew.toString());
                Selection.setSelection(cardNumber.getText(), dataNumberNew.toString().length());

            } else if (dataNumberNew.length() == 7) {
                dataNumberNew = dataNumberNew + " ";
                cardNumber.setText(dataNumberNew.toString());
                Selection.setSelection(cardNumber.getText(), dataNumberNew.toString().length());
            } else if (dataNumberNew.length() == 12) {
                dataNumberNew = dataNumberNew + " ";
                cardNumber.setText(dataNumberNew.toString());
                Selection.setSelection(cardNumber.getText(), dataNumberNew.toString().length());
            } else if (dataNumberNew.length() == 17) {
                dataNumberNew = dataNumberNew + " ";
                cardNumber.setText(dataNumberNew.toString());
                Selection.setSelection(cardNumber.getText(), dataNumberNew.toString().length());
            }else{

            }

            dataNumberOld = dataNumberNew;
        } else {
            /**
             * Proceso de brrado, elimina es caracter siguiente a la izquierda, despues de que se
             * borra de manera automatica el espacio al orpimir la flecha
             */
            if (dataNumberNew.length() == 17) {
                dataNumberNew = dataNumberNew.substring(0, dataNumberNew.length() -1);
                cardNumber.setText(dataNumberNew.toString());
                Selection.setSelection(cardNumber.getText(), dataNumberNew.toString().length());

            } else if (dataNumberNew.length() == 12) {
                dataNumberNew = dataNumberNew.substring(0, dataNumberNew.length() -1);
                cardNumber.setText(dataNumberNew.toString());
                Selection.setSelection(cardNumber.getText(), dataNumberNew.toString().length());
            } else if (dataNumberNew.length() == 7) {
                dataNumberNew = dataNumberNew.substring(0, dataNumberNew.length() -1);
                cardNumber.setText(dataNumberNew.toString());
                Selection.setSelection(cardNumber.getText(), dataNumberNew.toString().length());
            } else if (dataNumberNew.length() == 3) {
                dataNumberNew = dataNumberNew.substring(0, dataNumberNew.length() -1);
                cardNumber.setText(dataNumberNew.toString());
                Selection.setSelection(cardNumber.getText(), dataNumberNew.toString().length());
            }

            dataNumberOld = dataNumberNew;
        }
    }
}