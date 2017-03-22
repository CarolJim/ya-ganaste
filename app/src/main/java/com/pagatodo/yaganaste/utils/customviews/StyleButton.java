package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.pagatodo.yaganaste.utils.FontCache;


/**
 * Created by jcortez on 14/10/2015.
 */
public class StyleButton extends MaterialButton {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public StyleButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public StyleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void applyCustomFont(Context context) {
//        super.applyCustomFont(context);

        Typeface customFont = FontCache.getTypeface("fonts/Gotham/Gotham-Medium.ttf", context);
        setTypeface(customFont);
    }
/*
    private Typeface selectTypeface(Context context, int textStyle) {

        switch (textStyle) {
            case 1: // bold
                return FontCache.getTypeface("fonts/Roboto-Bold.ttf", context);

            case 2: // italic
                return FontCache.getTypeface("fonts/Roboto-Italic.ttf", context);

            case 0: // regular
            default:
                return FontCache.getTypeface("fonts/Roboto-Regular.ttf", context);
        }
    }*/
}
