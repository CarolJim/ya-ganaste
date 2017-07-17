package com.pagatodo.yaganaste.utils;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by flima on 09/03/2017.
 */

public class NumberCardTextWatcher implements TextWatcher {

    String dataNumberNew;
    String dataNumberOld;
    int countData;
    EditText cardNumber;

    public NumberCardTextWatcher() {
    }

    public NumberCardTextWatcher(EditText cardNumber) {
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

    @Override
    public void afterTextChanged(Editable s) {
        dataNumberNew = s.toString();
        if (dataNumberNew.length() > dataNumberOld.length()) {
            Log.d("NumberTextWatcher", "dataNumberNew Es mayor");

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
            }else{

            }

            countData++;
            dataNumberOld = dataNumberNew;
        } else {
            Log.d("NumberTextWatcher", "dataNumberNew Es menor");

            if (dataNumberNew.length() == 14) {
                dataNumberNew = dataNumberNew.substring(0, dataNumberNew.length() -1);
                cardNumber.setText(dataNumberNew.toString());
                Selection.setSelection(cardNumber.getText(), dataNumberNew.toString().length());

            } else if (dataNumberNew.length() == 9) {
                dataNumberNew = dataNumberNew.substring(0, dataNumberNew.length() -1);
                cardNumber.setText(dataNumberNew.toString());
                Selection.setSelection(cardNumber.getText(), dataNumberNew.toString().length());
            } else if (dataNumberNew.length() == 4) {
                dataNumberNew = dataNumberNew.substring(0, dataNumberNew.length() -1);
                cardNumber.setText(dataNumberNew.toString());
                Selection.setSelection(cardNumber.getText(), dataNumberNew.toString().length());
            }

            countData--;
            dataNumberOld = dataNumberNew;
        }
    }
}