package com.pagatodo.yaganaste.utils.customviews.yaganasteviews;

import android.content.Context;
import androidx.appcompat.widget.LinearLayoutCompat;
import android.util.AttributeSet;

/**
 * @author Juan Guerra on 19/04/2017.
 */

public abstract class TabViewElement extends LinearLayoutCompat {

    public TabViewElement(Context context) {
        super(context);
    }

    public TabViewElement(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabViewElement(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract void updateData();
}
