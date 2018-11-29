package com.pagatodo.yaganaste.utils.customviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;

public class CustomPassSixDigits extends LinearLayout {

    OnCodeChangedListener listener;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;
    private TextView text5;
    private TextView text6;
    private Boolean isValid;

    public CustomPassSixDigits(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomPassSixDigits(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomPassSixDigits(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.pass_six_digits, this);
        text1 = findViewById(R.id.textPass1);
        text2 = findViewById(R.id.textPass2);
        text3 = findViewById(R.id.textPass3);
        text4 = findViewById(R.id.textPass4);
        text5 = findViewById(R.id.textPass5);
        text6 = findViewById(R.id.textPass6);

        StringBuilder sb = new StringBuilder();
        text1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!text1.getText().toString().isEmpty()) {
                    text1.setBackgroundResource(R.drawable.ico_asterisk);
                    //text1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ico_asterisk,0,0,0);

                    text2.requestFocus();
                    if (listener != null) {
                        listener.onCodeChanged();
                    }
                }else {
                    text1.setBackgroundResource(R.drawable.border);
                }
            }
        });
        text2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!text2.getText().toString().isEmpty()) {
                    text2.setBackgroundResource(R.drawable.ico_asterisk);
                    text3.requestFocus();
                    if (listener != null) {
                        listener.onCodeChanged();
                    }
                }else {
                    text1.requestFocus();

                    text2.setBackgroundResource(R.drawable.border);
                }
            }
        });
        text3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!text3.getText().toString().isEmpty()) {
                    text3.setBackgroundResource(R.drawable.ico_asterisk);
                    text4.requestFocus();
                    if (listener != null) {
                        listener.onCodeChanged();
                    }
                }else {
                    text2.requestFocus();
                    text3.setBackgroundResource(R.drawable.border);
                }
            }
        });
        text4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!text4.getText().toString().isEmpty()) {
                    text4.setBackgroundResource(R.drawable.ico_asterisk);
                    text5.requestFocus();
                    if (listener != null) {
                        listener.onCodeChanged();
                    }
                }else {
                    text3.requestFocus();
                    text4.setBackgroundResource(R.drawable.border);
                }
            }
        });
        text5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!text5.getText().toString().isEmpty()) {
                    text5.setBackgroundResource(R.drawable.ico_asterisk);
                    text6.requestFocus();
                    if (listener != null) {
                        listener.onCodeChanged();
                    }
                }else {
                    text4.requestFocus();
                    text5.setBackgroundResource(R.drawable.border);
                }
            }
        });
        text6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!text6.getText().toString().isEmpty()) {
                    text6.setBackgroundResource(R.drawable.ico_asterisk);
                    InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(text6.getWindowToken(), 0);
                } else {
                    text5.requestFocus();
                    text6.setBackgroundResource(R.drawable.border);
                }
                if (listener != null) {
                    listener.onCodeChanged();
                }
            }
        });
    }

    public interface OnCodeChangedListener {
        void onCodeChanged();
    }
}
