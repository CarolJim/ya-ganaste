package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by jvazquez on 08/02/2017.
 */

public class SquareAppCompatImageView extends AppCompatImageView {
    public SquareAppCompatImageView(Context context) {
        super(context);
    }

    public SquareAppCompatImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareAppCompatImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
