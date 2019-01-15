package com.pagatodo.yaganaste.utils;

import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

import com.pagatodo.yaganaste.App;

/**
 * @author Juan Guerra on 07/08/2017.
 */

public class SpanTextStyle {
    private String text;
    @StyleRes
    private int style;

    public SpanTextStyle(String text, int style) {
        this.text = text;
        this.style = style;
    }

    public SpanTextStyle(@StringRes int text, int style) {
        this.text = App.getInstance().getString(text);
        this.style = style;
    }

    public String getText() {
        return text;
    }

    public int getStyle() {
        return style;
    }
}
