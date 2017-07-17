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

            if (dataNumberNew.length() == 15) {
                dataNumberNew = dataNumberNew.substring(0, dataNumberNew.length() -1);
                cardNumber.setText(dataNumberNew.toString());
                Selection.setSelection(cardNumber.getText(), dataNumberNew.toString().length());

            } else if (dataNumberNew.length() == 10) {
                dataNumberNew = dataNumberNew.substring(0, dataNumberNew.length() -1);
                cardNumber.setText(dataNumberNew.toString());
                Selection.setSelection(cardNumber.getText(), dataNumberNew.toString().length());
            } else if (dataNumberNew.length() == 5) {
                dataNumberNew = dataNumberNew.substring(0, dataNumberNew.length() -1);
                cardNumber.setText(dataNumberNew.toString());
                Selection.setSelection(cardNumber.getText(), dataNumberNew.toString().length());
            }

            countData--;
            dataNumberOld = dataNumberNew;
        }
        /*if (!s.toString().matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
            String userInput = "" + s.toString().replaceAll("[^\\d]", "");
            StringBuilder cashAmountBuilder = new StringBuilder(userInput);

            while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                cashAmountBuilder.deleteCharAt(0);
            }

            while (cashAmountBuilder.length() < 3) {
                cashAmountBuilder.insert(0, '0');
            }
            *//*Format comas*//*
            if (cashAmountBuilder.length() > 5 && cashAmountBuilder.length() < 9) {
                cashAmountBuilder.insert(cashAmountBuilder.length() - 5, ',');
            }

            cashAmountBuilder.insert(cashAmountBuilder.length() - 2, '.');
            cashAmountBuilder.insert(0, '$');

            edtText.setText(cashAmountBuilder.toString());
            // keeps the cursor always to the right
            Selection.setSelection(edtText.getText(), cashAmountBuilder.toString().length());
        }*/
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}