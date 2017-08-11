package com.pagatodo.yaganaste.utils.customviews;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by Jordan on 11/08/2017.
 */

public abstract class CustomClickableSpan extends ClickableSpan {
    /**
     * Performs the click action associated with this span.
     */
    public abstract void onClick(View widget);

    private TextPaint textPaint;

    /**
     * Makes the text underlined and in the link color.
     */
    @Override
    public void updateDrawState(TextPaint tp) {
        this.textPaint = tp;
        this.textPaint.setColor(tp.linkColor);
    }

    public void setUnderlineText(boolean isUnderlineText) {
        this.textPaint.setUnderlineText(isUnderlineText);
    }

}
