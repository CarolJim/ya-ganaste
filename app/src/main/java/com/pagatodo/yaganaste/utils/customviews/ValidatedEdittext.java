package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.FontCache;

/**
 * Created by Jordan on 27/03/2017.
 */

public class ValidatedEdittext extends AppCompatEditText {
    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "\\d{3}-\\d{7}";
    private TYPE type;


    public ValidatedEdittext(Context context) {
        super(context);
        setCustomFont(context);

    }

    public ValidatedEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
        handlAttrs(context, attrs);
        setCustomFont(context);
    }

    public ValidatedEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handlAttrs(context, attrs);
        setCustomFont(context);
    }

    private void setCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/roboto/Roboto-Light.ttf", context);
        setTypeface(customFont);

    }


    private void setValidation(Context context, AttributeSet attrs) {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void handlAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ValidatedEdittext);
        int typeName = typedArray.getInt(R.styleable.ValidatedEdittext_tipovalidacion, -1);
        if (typeName >= 0) {
            switch (typeName) {
                case 0:
                    type = TYPE.EMAIL;
                    break;
                case 1:
                    type = TYPE.PASSWORD;
                    break;
                case 2:
                    type = TYPE.PHONE;
                    break;
                case 3:
                    type = TYPE.ZIPCODE;
                    break;
                case 4:
                    type = TYPE.TEXT;
                    break;
            }
        }
    }

    enum TYPE {EMAIL, PASSWORD, PHONE, ZIPCODE, TEXT}
}
