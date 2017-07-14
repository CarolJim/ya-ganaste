package com.pagatodo.yaganaste.utils.customviews;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.FontCache;

import static com.pagatodo.yaganaste.R.id.Forward;


/**
 * Created by jcortez on 14/10/2015.
 */
public class StyleButton extends AppCompatButton implements View.OnClickListener, ViewTreeObserver.OnGlobalLayoutListener {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";
    private OnClickListener mClickListener;
    private boolean interceptor;

    private static final int TYPE_FORWARD = 1;
    private static final int TYPE_BACK = 2;
    private static final int TYPE_NONE = 0;

    private int type;




    public StyleButton(Context context) {
        super(context);
        init(context, null);
        interceptor = true;
        Typeface customFont =  selectTypeface(context, 0); //FontCache.getTypeface("fonts/Gotham/Gotham-Medium.otf", context);
        setTypeface(customFont);
        super.setOnClickListener(this);
    }




    public StyleButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface customFont =  selectTypeface(context, 0); //FontCache.getTypeface("fonts/Gotham/Gotham-Medium.otf", context);
        setTypeface(customFont);
        super.setOnClickListener(this);
        init(context, attrs);
        setPadding(context, attrs);
    }

    public StyleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface customFont =  selectTypeface(context, 0); //FontCache.getTypeface("fonts/Gotham/Gotham-Medium.otf", context);
        setTypeface(customFont);
        super.setOnClickListener(this);
        init(context, attrs);
        setPadding(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray configurationParams =
                context.getTheme().obtainStyledAttributes(attrs, R.styleable.StyleButton, 0, 0);
        interceptor = configurationParams.getBoolean(R.styleable.MaterialButton_intercepterOnclick, false);

        setBackground(configurationParams.getDrawable(R.styleable.StyleButton_android_background));
        setTextColor(configurationParams.getColor(R.styleable.StyleButton_android_textColor,
                ContextCompat.getColor(getContext(), R.color.black)));

        this.type = configurationParams.getInt(R.styleable.StyleButton_typeButton, TYPE_NONE);
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

    private void setPadding(Context context, AttributeSet attrs) {
        TypedArray configurationParams = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StyleButton, 0, 0);
        this.type = configurationParams.getInt(R.styleable.StyleButton_typeButton, TYPE_NONE);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
        Log.d("Width: ", String.valueOf(getWidth()));
        Log.d("Height: ", String.valueOf(getHeight()));
        setPadding( type == TYPE_BACK ? (int) (getWidth() * .08) : 0, 0,
                type == TYPE_FORWARD ? (int) (getWidth() * .08) : 0, 0);
    }

}