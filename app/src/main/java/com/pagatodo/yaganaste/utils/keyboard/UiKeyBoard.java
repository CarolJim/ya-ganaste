package com.pagatodo.yaganaste.utils.keyboard;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.pagatodo.yaganaste.App;

import java.util.Objects;

public class UiKeyBoard {
    public static void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) App.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(){
        InputMethodManager imm = (InputMethodManager)  App.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
