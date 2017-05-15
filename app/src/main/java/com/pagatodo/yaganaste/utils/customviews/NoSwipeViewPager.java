package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Gorro on 21/07/16.
 */
public class NoSwipeViewPager extends ViewPager {

    private boolean swipeable;
    public NoSwipeViewPager(Context context) {
        this(context, null);
    }

    public NoSwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        swipeable = false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return swipeable && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return swipeable && super.onTouchEvent(ev);
    }

    public void setIsSwipeable(boolean swipeable) {
        this.swipeable = swipeable;
    }

  
}
