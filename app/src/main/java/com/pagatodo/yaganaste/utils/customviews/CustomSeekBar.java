package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.FontCache;

/**
 * Created by Jordan on 10/05/2017.
 */

public class CustomSeekBar extends android.support.v7.widget.AppCompatSeekBar {
    private int mThumbSize;
    private Paint mTextPaint;
    private String titleText;
    private Rect bounds;
    private Rect boundsLowerCase;

    public CustomSeekBar(Context context) {
        super(context);
    }

    public CustomSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.seekBarStyle);
    }

    public CustomSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        mThumbSize = (int) getResources().getDimension(R.dimen.seekbar_thumb_width);
        Typeface typeface = FontCache.getTypeface("fonts/roboto/Roboto-Regular.ttf", context);
        bounds = new Rect();
        boundsLowerCase = new Rect();
        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.WHITE);

        mTextPaint.setTypeface(typeface);
        mTextPaint.setTextAlign(Paint.Align.CENTER);


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomSeekBar);
        titleText = a.getString(R.styleable.CustomSeekBar_text);
        if (titleText == null) {
            titleText = "";
        }

        float textSize = a.getDimension(R.styleable.CustomSeekBar_textSize, getResources().getDimension(R.dimen.thumb_text_size));
        mTextPaint.setTextSize(textSize);
        //mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.getTextBounds(titleText.toLowerCase(), 0, titleText.length(), boundsLowerCase);
        mTextPaint.getTextBounds(titleText, 0, titleText.length(), bounds);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int leftPadding = getPaddingLeft() - getThumbOffset();
        int rightPadding = getPaddingRight() - getThumbOffset();
        int width = getWidth() - leftPadding - rightPadding;
        float progressRatio = (float) getProgress() / getMax();
        float thumbOffset = mThumbSize * (.5f - progressRatio);
        float thumbX = progressRatio * width + leftPadding + thumbOffset;
        float thumbY = (getHeight() / 2) + (boundsLowerCase.height() * 0.5f);//(getHeight() / 2) + (((bounds.height() + boundsLowerCase.height()) / 2) / 2); //(getHeight() / 2f) + (bounds.height() * 0.4f);
        canvas.drawText(titleText, thumbX, thumbY, mTextPaint);
    }
}
