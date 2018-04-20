package com.pagatodo.yaganaste.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.pagatodo.yaganaste.interfaces.ITextChangeListener;

import static com.pagatodo.yaganaste.utils.Recursos.SPACE;

/**
 * Created by asandovals on 17/04/2018.
 */

public class TarjetaStarbucksTextWatcher implements TextWatcher{

    public String TAG= this.getClass().getSimpleName();
    protected EditText editText;
    private ITextChangeListener listener;
    private String finalText="",auxText="";

    public TarjetaStarbucksTextWatcher(EditText e){
        editText=e;
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
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        auxText= s.toString().replaceAll(" ","");
        if (auxText.length()>16){
         editText.removeTextChangedListener(this);
         String subString = String.valueOf(s.subSequence(0,16));
         editText.setText(subString);
         editText.addTextChangedListener(this);
         finalText = subString;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        editText.removeTextChangedListener(this);
        String response="";
        if (finalText.length()>0){
            response=StringUtils.format(finalText.toString().replaceAll(" ",""),SPACE,4,4,4,4);
        }else {
            response = StringUtils.format(s.toString().replaceAll(" ", ""), SPACE, 4, 4,4,4);
        }
        editText.setText(response);
        editText.setSelection(response.length());
        editText.addTextChangedListener(this);

        if (listener != null) {
            listener.onTextChanged();
            if (response.length() == 19) {
                listener.onTextComplete();
            }
        }
    }
}
