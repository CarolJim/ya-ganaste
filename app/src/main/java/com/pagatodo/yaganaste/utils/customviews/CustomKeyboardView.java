package com.pagatodo.yaganaste.utils.customviews;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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

    public void setKeyBoard(Activity activity, int xmlLayoutResId){
        this.activity = activity;
        keyboard = new Keyboard(activity,xmlLayoutResId);
        this.setKeyboard(keyboard);
        this.setOnKeyboardActionListener(keyboardActionListener);
    }

    public void hideCustomKeyboard() {
        this.setVisibility(View.GONE);
        this.setEnabled(false);
    }
    public void showCustomKeyboard( View v) {
        this.setVisibility(View.VISIBLE);
        this.setEnabled(true);
        if( v!=null ){
            ((InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }


    public KeyboardView.OnKeyboardActionListener keyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int primaryCode) { }
        @Override
        public void onRelease(int primaryCode) { }
        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            CustomKeyboardView.setCodeKey(primaryCode);
            long eventTime = System.currentTimeMillis();
            KeyEvent event = new KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN, primaryCode, 0, 0, 0, 0, KeyEvent.FLAG_SOFT_KEYBOARD | KeyEvent.FLAG_KEEP_TOUCH_MODE);
            activity.dispatchKeyEvent(event);
        }
        @Override
        public void onText(CharSequence text) { }
        @Override
        public void swipeLeft() { }
        @Override
        public void swipeRight() { }
        @Override
        public void swipeDown() { }
        @Override
        public void swipeUp() { }
    };



}
