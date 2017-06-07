package com.pagatodo.yaganaste.utils.customviews;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.pagatodo.yaganaste.R;

import java.util.List;

/**
 * Created by flima on 06/04/2017.
 */

public class CustomKeyboardView extends KeyboardView {

    Keyboard keyboard;
    Activity activity;
    public static int codeKey;

    public static int getCodeKey() {
        return codeKey;
    }

    public static void setCodeKey(int codeKey) {
        CustomKeyboardView.codeKey = codeKey;
    }

    public CustomKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setKeyBoard(Activity activity, int xmlLayoutResId) {
        this.activity = activity;
        keyboard = new Keyboard(activity, xmlLayoutResId);

        this.setKeyboard(keyboard);
        this.setOnKeyboardActionListener(keyboardActionListener);


    }

    public void hideCustomKeyboard() {
        this.setVisibility(View.GONE);
        this.setEnabled(false);
    }

    public void showCustomKeyboard(View v) {
        this.setVisibility(View.VISIBLE);
        this.setEnabled(true);
        if (v != null) {
            ((InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        if (Build.VERSION.SDK_INT >= 23) {
            List<Keyboard.Key> keys = getKeyboard().getKeys();
            for (Keyboard.Key key : keys) {
                if (key.codes[0] == 29) {

                    Drawable dr = ContextCompat.getDrawable(getContext(), R.drawable.custom_key_point_selector); //new ColorDrawable(ContextCompat.getColor(getContext(), R.color.grayColor));
                    dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                    dr.setState(key.getCurrentDrawableState());
                    dr.draw(canvas);
                } else if (key.codes[0] == 67) {
                    Drawable dr = ContextCompat.getDrawable(getContext(), R.drawable.custom_key_back_selector); //new ColorDrawable(ContextCompat.getColor(getContext(), R.color.grayColor));
                    dr.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                    dr.setState(key.getCurrentDrawableState());
                    dr.draw(canvas);
                }

            }
        }
    }


    private Drawable getDrawable() {
        GradientDrawable normalBackground = new GradientDrawable();
        normalBackground.setShape(GradientDrawable.RECTANGLE);
        normalBackground.setColor(ContextCompat.getColor(getContext(), R.color.bg_progress));
        normalBackground.setAlpha(128);
        return normalBackground;
    }


    private Drawable getPressedDrawable() {
        GradientDrawable pressedDrawable = new GradientDrawable();
        pressedDrawable.setShape(GradientDrawable.RECTANGLE);
        pressedDrawable.setColor(Color.WHITE);
        pressedDrawable.setAlpha(128);

        return pressedDrawable;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Drawable getMaterialDrawable() {

        GradientDrawable normalBackground = new GradientDrawable();
        normalBackground.setShape(GradientDrawable.RECTANGLE);
        normalBackground.setColor(ContextCompat.getColor(getContext(), R.color.bg_progress));

        return normalBackground;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Drawable getPressedMaterialDrawable() {


        GradientDrawable pressedDrawable = new GradientDrawable();
        pressedDrawable.setShape(GradientDrawable.RECTANGLE);
        pressedDrawable.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));

        return new RippleDrawable(ColorStateList.valueOf(Color.WHITE),
                pressedDrawable, null);
    }


    public KeyboardView.OnKeyboardActionListener keyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            /**
             * Guardamos el codigo de la tecla que usamos en el CustomKeyboard
             */
            CustomKeyboardView.setCodeKey(primaryCode);

            long eventTime = System.currentTimeMillis();
            KeyEvent event = new KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN, primaryCode, 0, 0, 0, 0, KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE);
            activity.dispatchKeyEvent(event);
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void swipeUp() {
        }
    };


}
