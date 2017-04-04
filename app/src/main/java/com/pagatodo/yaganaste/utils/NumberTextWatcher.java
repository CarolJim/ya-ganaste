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
    public NumberTextWatcher(EditText editText) {
        this.edtText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!s.toString().matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$"))
        {
            String userInput= ""+s.toString().replaceAll("[^\\d]", "");
            StringBuilder cashAmountBuilder = new StringBuilder(userInput);

            while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                cashAmountBuilder.deleteCharAt(0);
            }

            while (cashAmountBuilder.length() < 3) {
                cashAmountBuilder.insert(0, '0');
            }
            /*Format comas*/
            if(cashAmountBuilder.length() > 5 && cashAmountBuilder.length() < 9) {
                cashAmountBuilder.insert( cashAmountBuilder.length() - 5, ',');
            }

            cashAmountBuilder.insert(cashAmountBuilder.length()-2, '.');
            cashAmountBuilder.insert(0, '$');

            edtText.setText(cashAmountBuilder.toString());
            // keeps the cursor always to the right
            Selection.setSelection(edtText.getText(), cashAmountBuilder.toString().length());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}