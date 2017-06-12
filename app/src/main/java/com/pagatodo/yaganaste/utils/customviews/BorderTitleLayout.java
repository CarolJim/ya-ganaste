package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.pagatodo.yaganaste.R;

/**
 * Created by Jordan on 29/03/2017.
 */

public class BorderTitleLayout extends RelativeLayout {

    private String title;

    public BorderTitleLayout(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public BorderTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BorderTitleLayout);
        title = typedArray.getString(R.styleable.BorderTitleLayout_title).trim();
        typedArray.recycle();
    }

    public BorderTitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BorderTitleLayout);
        title = typedArray.getString(R.styleable.BorderTitleLayout_title).trim();
        typedArray.recycle();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BorderTitleLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setWillNotDraw(false);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BorderTitleLayout);
        title = typedArray.getString(R.styleable.BorderTitleLayout_title).trim();
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto/Roboto-Regular.ttf");
        int textSize = (int) (18 * getContext().getResources().getDisplayMetrics().scaledDensity);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.borderLayoutColor));

        paint.setTextSize(textSize);
        paint.setTypeface(typeface);
        paint.setTextAlign(Paint.Align.CENTER);


        Rect bounds = new Rect();
        paint.getTextBounds(title, 0, title.length(), bounds);

        float singleLetterWidth = paint.measureText("A");

        int widthText = bounds.width();
        int heightText = bounds.height();


        int left = getPaddingLeft();
        int top = (int) (getPaddingTop() + (heightText * 0.6));
        int right = getWidth() - getPaddingRight();
        int bottom = getHeight() - getPaddingBottom();


        int midleRight = (int) ((getWidth() / 2) + (widthText / 2) + singleLetterWidth);
        int midleLeft = (int) ((getWidth() / 2) - (widthText / 2) - singleLetterWidth);

        canvas.drawText(title, getWidth() / 2, getPaddingTop() + heightText, paint);


        paint.setStrokeWidth(2f);
        canvas.drawLine(left, top, left, bottom, paint);
        canvas.drawLine(left, bottom, right, bottom, paint);
        canvas.drawLine(right, top, right, bottom, paint);
        canvas.drawLine(left, top, midleLeft, top, paint);
        canvas.drawLine(midleRight, top, right, top, paint);
    }

    public void setTitle(String title) {
        this.title = title;
    }
}