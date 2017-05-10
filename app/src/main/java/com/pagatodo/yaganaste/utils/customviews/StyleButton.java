package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.FontCache;


/**
 * Created by jcortez on 14/10/2015.
 */
public class StyleButton extends AppCompatButton implements View.OnClickListener{

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";
    private OnClickListener mClickListener;
    private boolean interceptor;


    public StyleButton(Context context) {
        super(context);
        Typeface customFont =  selectTypeface(context, 0); //FontCache.getTypeface("fonts/Gotham/Gotham-Medium.otf", context);
        setTypeface(customFont);
        super.setOnClickListener(this);
        interceptor = true;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StyleButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface customFont =  selectTypeface(context, 0); //FontCache.getTypeface("fonts/Gotham/Gotham-Medium.otf", context);
        setTypeface(customFont);
        super.setOnClickListener(this);
        init(context, attrs);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StyleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface customFont =  selectTypeface(context, 0); //FontCache.getTypeface("fonts/Gotham/Gotham-Medium.otf", context);
        setTypeface(customFont);
        super.setOnClickListener(this);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray configurationParams =
                context.getTheme().obtainStyledAttributes(attrs, R.styleable.StyleButton, 0, 0);
        interceptor = configurationParams.getBoolean(R.styleable.MaterialButton_intercepterOnclick, false);
    }

//    @Override
//    protected void applyCustomFont(Context context) {
////        super.applyCustomFont(context);
//
//        Typeface customFont =  selectTypeface(context, 0); //FontCache.getTypeface("fonts/Gotham/Gotham-Medium.otf", context);
//        setTypeface(customFont);
//    }

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
    }
    public void eneableButton() {
        setClickable(false);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setClickable(true);
            }
        }, 1500);
    }
    @Override
    public void setOnClickListener(OnClickListener l) {
        this.mClickListener = l;
    }

    @Override
    public void onClick(View view) {
        if (mClickListener != null) {
            mClickListener.onClick(this);
            if (interceptor) eneableButton();
        }
    }
}
