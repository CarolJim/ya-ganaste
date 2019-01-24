package com.pagatodo.view_manager.components.inputs;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.view_manager.R;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

public class InputCardNumber extends LinearLayout implements Input, View.OnFocusChangeListener,TextWatcher {

    private TextView textHint;
    private AppCompatEditText editTextInput;
    private InputSecretListener listener;
    private LinearLayout textLayer;

    public InputCardNumber(Context context) {
        super(context);
        initView(null);
    }

    public InputCardNumber(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public InputCardNumber(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }


    @Override
    public void initView(AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rootView = inflater.inflate(R.layout.input_card,this,false);
        textHint = rootView.findViewById(R.id.text_hint);
        editTextInput = rootView.findViewById(R.id.edit_text_input);
        //viewMain = rootView.findViewById(R.id.main);
        textLayer = rootView.findViewById(R.id.text_card_layer);

        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.InputCardNumber,
                    0, 0);
            try {
                String resText = a.getString(R.styleable.InputCardNumber_labelHintInput);
                setHint(resText);
            } finally {
                a.recycle();
            }
        }
        bind();
        this.addView(rootView);
    }

    private void bind(){
        editTextInput.setOnFocusChangeListener(this);
        editTextInput.addTextChangedListener(this);
        setRequest();
    }

    public void setRequest(){
        editTextInput.requestFocus();
    }

    public AppCompatEditText getEditText(){
        return this.editTextInput;
    }

    @Override
    public void active() {
        this.setBackgroundResource(R.drawable.input_text_active);
    }

    @Override
    public void desactive() {
        this.setBackgroundResource(R.drawable.input_text_normal);
    }

    @Override
    public void setHint(String hintText) {
        textHint.setText(hintText);
    }

    @Override
    public void isError() {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            active();
        } else {
            desactive();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int lengh = Objects.requireNonNull(editTextInput.getText()).toString().length();
        String text = editTextInput.getText().toString();
        if (lengh > 0 && lengh < 16) {
            ((TextView) textLayer.getChildAt(lengh )).setText("");
            ((TextView) textLayer.getChildAt(lengh - 1)).setText(text.substring(lengh - 1));
            this.listener.inputListenerBegin();
        } else if (lengh == 0){
            ((TextView) textLayer.getChildAt(0)).setText("");
            this.listener.inputListenerBegin();
        } else if (lengh == 16){
            ((TextView) textLayer.getChildAt(15)).setText(text.substring(15));
            this.listener.inputListenerFinish();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void setInputSecretListener(InputSecretListener listener) {
        this.listener = listener;
    }
}
