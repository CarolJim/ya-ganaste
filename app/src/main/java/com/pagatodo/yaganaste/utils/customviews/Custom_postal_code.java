package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;

public class Custom_postal_code extends LinearLayout implements View.OnKeyListener {

    private EditText tex1, tex2, tex3, tex4, tex5, add;
    private Integer result;
    OnCodeChangedListenerCP listener;
    int contador = 0;

    public void setListener(OnCodeChangedListenerCP listener) {
        this.listener = listener;
    }


    public Custom_postal_code(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Custom_postal_code(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Custom_postal_code(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        View.inflate(context, R.layout.custom_code_postal, this);
        tex1 = findViewById(R.id.edt1);
        tex2 = findViewById(R.id.edt2);
        tex3 = findViewById(R.id.edt3);
        tex4 = findViewById(R.id.edt4);
        tex5 = findViewById(R.id.edt5);
        add = findViewById(R.id.aux_codepostal);
        result = 0;

        add.setOnFocusChangeListener((view1, b) -> {
            if (b) {
                if (!add.getText().toString().isEmpty()) {
                    StringBuilder cad = new StringBuilder(add.getText().toString());
                    cad.deleteCharAt(result);
                    add.setText(cad);
                    add.setSelection(result);
                }
            }
        });

        add.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                addNumber(editable.toString().substring(editable.length() - 1));
                result++;
            }
        });
        tex1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (tex1.getText().toString().trim().length() == 1) {
                    tex2.requestFocus();
                }
                if (listener != null) {
                    listener.onCodeChanged();
                }
            }
        });
        tex2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (tex2.getText().toString().trim().length() == 1) {
                    tex3.requestFocus();

                }
                if (listener != null) {
                    listener.onCodeChanged();
                }
            }
        });
        tex3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (tex3.getText().toString().trim().length() == 1) {
                    tex4.requestFocus();
                }
                if (listener != null) {
                    listener.onCodeChanged();
                }
            }
        });
        tex4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (tex4.getText().toString().trim().length() == 1) {
                    tex5.requestFocus();
                }
                if (listener != null) {
                    listener.onCodeChanged();
                }
            }
        });
        tex5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (tex5.getText().toString().trim().length() == 1) {
                }
                if (listener != null) {
                    listener.onCodeChanged();
                }
            }
        });

        tex2.setOnKeyListener(this::onKey);
        tex3.setOnKeyListener(this::onKey);
        tex4.setOnKeyListener(this::onKey);
        tex5.setOnKeyListener(this::onKey);


    }


    public String getText() {
        String code;
        code = tex1.getText().toString() + tex2.getText().toString() + tex3.getText().toString() +
                tex4.getText().toString() + tex5.getText().toString();
        return code;
    }

    public EditText getCode1() {
        return (EditText) tex1;
    }

    public EditText getCode2() {
        return (EditText) tex2;
    }

    public EditText getCode3() {
        return (EditText) tex3;
    }

    public EditText getCode4() {
        return (EditText) tex4;
    }

    public EditText getCode5() {
        return (EditText) tex5;
    }

    public void addNumber(String s) {

        switch (result) {
            case 0:
                tex1.setText(s);
                break;
            case 1:
                tex2.setText(s);
                break;
            case 2:
                tex3.setText(s);
                break;
            case 3:
                tex4.setText(s);
                break;
            case 4:
                tex5.setText(s);
                break;
        }
    }


    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (esPar(contador)) {
            switch (view.getId()) {
                case R.id.edt2:
                    if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                        if (tex2.getText().length() == 0 && tex2.hasFocus()) {
                            tex1.setText("");
                            tex1.requestFocus();
                        }
                    }
                    break;
                case R.id.edt3:
                    if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                        if (tex3.getText().length() == 0 && tex3.hasFocus()) {
                            tex2.setText("");
                            tex2.requestFocus();
                        }
                    }
                    break;
                case R.id.edt4:
                    if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                        if (tex4.getText().length() == 0 && tex4.hasFocus()) {
                            tex3.setText("");
                            tex3.requestFocus();
                        }
                    }
                    break;
                case R.id.edt5:
                    if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                        if (tex5.getText().length() == 0 && tex5.hasFocus()) {
                            tex4.setText("");
                            tex4.requestFocus();
                        }
                    }
                    break;
            }
        }
        contador++;
        return false;
    }


    public interface OnCodeChangedListenerCP {
        void onCodeChanged();
    }

    static boolean esPar(int numero) {
        if (numero % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }
}

