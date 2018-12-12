package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;

public class Custom_postal_code extends LinearLayout {

    private EditText tex1, tex2, tex3, tex4, tex5;


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
                } else {
                    tex1.requestFocus();
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
                else {

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
                else {
                    tex3.requestFocus();
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
                } else {
                    tex4.requestFocus();
                }
            }
        });
    }
}
