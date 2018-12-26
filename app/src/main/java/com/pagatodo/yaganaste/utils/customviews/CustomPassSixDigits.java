package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;

import static com.pagatodo.yaganaste.R.drawable.ico_asterisk_blank;

public class CustomPassSixDigits extends LinearLayout implements View.OnKeyListener, View.OnFocusChangeListener {

    OnCodeChangedListener listener;
    private EditText text1, text2, text3, text4, text5, text6, aux;
    private TextView titlepass;
    private Boolean isActionDelete = true;
    int position;

    LinearLayout linear_pass;

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

    public void setListener(OnCodeChangedListener listener) {
        this.listener = listener;
    }

    private void init(Context context, AttributeSet attrs) {
        //View view = LayoutInflater.from(context).inflate(R.layout.pass_six_digits, null, false);
        View.inflate(context, R.layout.pass_six_digits, this);
        text1 = findViewById(R.id.textPass1);
        text2 = findViewById(R.id.textPass2);
        text3 = findViewById(R.id.textPass3);
        text4 = findViewById(R.id.textPass4);
        text5 = findViewById(R.id.textPass5);
        text6 = findViewById(R.id.textPass6);
        aux = findViewById(R.id.edit_text_aux);
        titlepass = findViewById(R.id.titlepass);

        text1.setOnFocusChangeListener(this::onFocusChange);
        text2.setOnFocusChangeListener(this::onFocusChange);
        text3.setOnFocusChangeListener(this::onFocusChange);
        text4.setOnFocusChangeListener(this::onFocusChange);
        text5.setOnFocusChangeListener(this::onFocusChange);
        text6.setOnFocusChangeListener(this::onFocusChange);

        position = 0;
        linear_pass = findViewById(R.id.linear_pass);

        aux.setOnFocusChangeListener((view1, b) -> {
            if (b) {
                if (!aux.getText().toString().isEmpty()) {
                    StringBuilder cad = new StringBuilder(aux.getText().toString());
                    cad.deleteCharAt(position);
                    aux.setText(cad);
                    aux.setSelection(position);

                }

            }
        });
        aux.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //auxText.add();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                addNumber(editable.toString().substring(editable.length() - 1));
                position++;
            }
        });

        text1.setOnFocusChangeListener((view, focus) -> {
            if (focus) {
                linear_pass.setBackgroundResource(R.drawable.rectangle_blue_inputdata);
            } else {
                linear_pass.setBackgroundResource(R.drawable.inputtext_normal);
            }
        });
        text2.setOnFocusChangeListener((view, focus) -> {
            if (focus) {
                linear_pass.setBackgroundResource(R.drawable.rectangle_blue_inputdata);
            } else {
                linear_pass.setBackgroundResource(R.drawable.inputtext_normal);
            }
        });
        text3.setOnFocusChangeListener((view, focus) -> {
            if (focus) {
                linear_pass.setBackgroundResource(R.drawable.rectangle_blue_inputdata);
            } else {
                linear_pass.setBackgroundResource(R.drawable.inputtext_normal);
            }
        });
        text4.setOnFocusChangeListener((view, focus) -> {
            if (focus) {
                linear_pass.setBackgroundResource(R.drawable.rectangle_blue_inputdata);
            } else {
                linear_pass.setBackgroundResource(R.drawable.inputtext_normal);
            }
        });
        text5.setOnFocusChangeListener((view, focus) -> {
            if (focus) {
                linear_pass.setBackgroundResource(R.drawable.rectangle_blue_inputdata);
            } else {
                linear_pass.setBackgroundResource(R.drawable.inputtext_normal);
            }
        });
        text6.setOnFocusChangeListener((view, focus) -> {
            if (focus) {
                linear_pass.setBackgroundResource(R.drawable.rectangle_blue_inputdata);
            } else {
                linear_pass.setBackgroundResource(R.drawable.inputtext_normal);
            }
        });

        text1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                text1.requestFocus();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void afterTextChanged(Editable editable) {
                text1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_asterisk, 0);
                text2.requestFocus();
                if (listener != null) {
                    listener.onCodeChanged();
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
                    text2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_asterisk, 0);
                    text3.requestFocus();
                }
                if (listener != null) {
                    listener.onCodeChanged();
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
                    text3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_asterisk, 0);
                    text4.requestFocus();
                }
                if (listener != null) {
                    listener.onCodeChanged();
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
                    text4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_asterisk, 0);
                    text5.requestFocus();
                }
                if (listener != null) {
                    listener.onCodeChanged();
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
                    text5.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_asterisk, 0);
                    text6.requestFocus();
                }
                if (listener != null) {
                    listener.onCodeChanged();
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
                    text6.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_asterisk, 0);
                    InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(text6.getWindowToken(), 0);
                    listener.setVisivility();
                }
                if (listener != null) {
                    listener.onCodeChanged();
                }
            }
        });

        text2.setOnKeyListener(this::onKey);
        text3.setOnKeyListener(this::onKey);
        text4.setOnKeyListener(this::onKey);
        text5.setOnKeyListener(this::onKey);
        text6.setOnKeyListener(this::onKey);
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL && isActionDelete) {
            switch (view.getId()) {
                case R.id.textPass2:
                    if (text2.getText().length() == 0 && text2.hasFocus()) {
                        text1.setText("");
                        text1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_asterisk_blank, 0);
                        isActionDelete = false;
                        text1.requestFocus();
                    }
                    break;
                case R.id.textPass3:
                    if (text3.getText().length() == 0 && text3.hasFocus()) {
                        text2.setText("");
                        text2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_asterisk_blank, 0);
                        isActionDelete = false;
                        text2.requestFocus();
                    }
                    break;
                case R.id.textPass4:
                    if (text4.getText().length() == 0 && text4.hasFocus()) {
                        text3.setText("");
                        text3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_asterisk_blank, 0);
                        isActionDelete = false;
                        text3.requestFocus();
                    }
                    break;
                case R.id.textPass5:
                    if (text5.getText().length() == 0 && text5.hasFocus()) {
                        text4.setText("");
                        text4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_asterisk_blank, 0);
                        isActionDelete = false;
                        text4.requestFocus();
                    }
                    break;
                case R.id.textPass6:
                    if (text6.getText().length() == 0 && text6.hasFocus()) {
                        text5.setText("");
                        text5.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_asterisk_blank, 0);
                        isActionDelete = false;
                        text5.requestFocus();
                    }
                    break;
            }
        } else {
            isActionDelete = true;
        }
        return false;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            if (view.getId() == R.id.textPass1)
                position = 0;
            if (view.getId() == R.id.textPass2)
                position = 1;
            if (view.getId() == R.id.textPass3)
                position = 2;
            if (view.getId() == R.id.textPass4)
                position = 3;
            if (view.getId() == R.id.textPass5)
                position = 4;
            if (view.getId() == R.id.textPass6)
                position = 5;

            EditText editText = (EditText) view;
            if (!editText.getText().toString().isEmpty()) {
                editText.setText("");
                aux.requestFocus();
            }
        }
    }

    public void clearCode() {
        text1.setText("");
        text1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_asterisk_blank, 0);

        text2.setText("");
        text2.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_asterisk_blank, 0);

        text3.setText("");
        text3.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_asterisk_blank, 0);

        text4.setText("");
        text4.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_asterisk_blank, 0);

        text5.setText("");
        text5.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_asterisk_blank, 0);

        text6.setText("");
        text6.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ico_asterisk_blank, 0);

        text1.requestFocus();
    }

    public void addNumber(String s) {
        switch (position) {
            case 0:
                text1.setText(s);
                break;
            case 1:
                text2.setText(s);
                break;
            case 2:
                text3.setText(s);
                break;
            case 3:
                text4.setText(s);
                break;
            case 4:
                text5.setText(s);
                break;
            case 5:
                text6.setText(s);
                break;
        }
    }

    public String getText() {
        String code;
        code = text1.getText().toString() + text2.getText().toString() + text3.getText().toString() +
                text4.getText().toString() + text5.getText().toString() + text6.getText().toString();
        return code;
    }

    public EditText getCode1() {
        return (EditText) text1;
    }

    public EditText getCode2() {
        return (EditText) text2;
    }

    public EditText getCode3() {
        return (EditText) text3;
    }

    public EditText getCode4() {
        return (EditText) text4;
    }

    public EditText getCode5() {
        return (EditText) text5;
    }

    public EditText getCode6() {
        return (EditText) text6;
    }

    public TextView getTitlepass(){
        return titlepass;
    }

    public interface OnCodeChangedListener {
        void onCodeChanged();

        void setVisivility();
    }
}
