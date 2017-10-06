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

public class CustomSeekBarEnviar extends android.support.v7.widget.AppCompatSeekBar {
    private int mThumbSize;
    private TextPaint mTextPaint;
    private String titleText;
    private Rect bounds;

    public CustomSeekBarEnviar(Context context) {
        super(context);
    }

    public CustomSeekBarEnviar(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.seekBarStyle);
    }

    public CustomSeekBarEnviar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        mThumbSize = (int) getResources().getDimension(R.dimen.seekbar_thumb_width);
        Typeface typeface = FontCache.getTypeface("fonts/roboto/Roboto-Regular.ttf", context);
        bounds = new Rect();
        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.WHITE);

        mTextPaint.setTypeface(typeface);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomSeekBar);
        titleText = a.getString(R.styleable.CustomSeekBar_text);
        if (titleText == null) {
            titleText = "";
        }
        //float textSize = a.getDimension(R.styleable.CustomSeekBar_textSize, getResources().getDimension(R.dimen.thumb_text_size));
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float textSize = getHeight() * 0.3f;// getResources().getDisplayMetrics().scaledDensity;
        mTextPaint.setTextSize(textSize);
        mTextPaint.getTextBounds(titleText, 0, titleText.length(), bounds);

        int leftPadding = getPaddingLeft() - getThumbOffset();
        int rightPadding = getPaddingRight() - getThumbOffset();
        int width = getWidth() - leftPadding - rightPadding;
        float progressRatio = (float) getProgress() / getMax();
        float thumbOffset = mThumbSize * (.45f - progressRatio);
        float thumbX = progressRatio * width + thumbOffset;//+ leftPadding
        float thumbY = (getHeight() + bounds.height() + ((mTextPaint.ascent() + mTextPaint.descent()) / 2)) * 0.55f;//;//(getHeight() / 2) + (((bounds.height() + boundsLowerCase.height()) / 2) / 2); //(getHeight() / 2f) + (bounds.height() * 0.4f);
        canvas.drawText(titleText, thumbX, thumbY, mTextPaint);
    }
}
