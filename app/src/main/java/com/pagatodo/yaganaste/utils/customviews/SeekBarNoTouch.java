package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.pagatodo.yaganaste.R;

import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.content.ContextCompat;

/**
 * @author Juan Guerra on 07/04/2017.
 */

public class SeekBarNoTouch extends AppCompatSeekBar {

    public SeekBarNoTouch(Context context) {
        this(context, null);
    }

    public SeekBarNoTouch(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.seekBarStyle);
    }

    public SeekBarNoTouch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            setThumb(ContextCompat.getDrawable(context, R.drawable.thumb_adquirente));
            Drawable drawable = getThumb();
            TypedArray configurationParams =
                    context.getTheme().obtainStyledAttributes(attrs, R.styleable.SeekBarNoTouch, 0, 0);
            int thumbColor = configurationParams.getColor(R.styleable.SeekBarNoTouch_seekThumbColor,
                    ContextCompat.getColor(context, R.color.colorPrimary));
            ((GradientDrawable) drawable).setColor(thumbColor);
            setThumb(drawable);
            requestLayout();
            configurationParams.recycle();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}