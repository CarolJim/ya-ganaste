package com.pagatodo.yaganaste.utils.customviews;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.pagatodo.yaganaste.R;

/**
 * Created by Dell on 25/07/2017.
 */

public class StatusViewCupo extends View {

    public static enum STATUS{
        FIRST,SECOND,THIRD
    }

    private float mPercentFirst  = 0;
    private float mPercentSecond = 0;
    private float mPercentThird  = 0;

    private float mStrokeWidth;
    private int mBgColor = 0xffe1e1e1;
    private float mStartAngle = 0;

    private int mColorFirst    = 0;
    private int mColorSecond   = 0;
    private int mColorThird    = 0;


    private Context mContext;
    private RectF mOval;
    private Paint mPaint;

    private ObjectAnimator animator;

    private LinearGradient mShaderFirst;
    private LinearGradient mShaderSecond;
    private LinearGradient mShaderThird;


    public StatusViewCupo(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.StatusCupoColors,
                0,0);

        try {
            mBgColor = a.getColor(R.styleable.StatusCupoColors_bgColor, 0xffe1e1e1);
            mColorFirst     = a.getColor(R.styleable.StatusCupoColors_firstColor, 0xffff4800);
            mColorSecond = a.getColor(R.styleable.StatusCupoColors_secondColor, 0xffff4800);
            mColorThird  = a.getColor(R.styleable.StatusCupoColors_thirdColor, 0xffff4800);


            mPercentFirst = a.getFloat(R.styleable.StatusCupoColors_percent, 0);
            mStartAngle = a.getFloat(R.styleable.StatusCupoColors_startAngle, 0)+270;
            mStrokeWidth = a.getDimensionPixelSize(R.styleable.StatusCupoColors_strokeWidth, dp2px(20));
        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private int dp2px(float dp) {
        return (int) (mContext.getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setShader(null);
        mPaint.setColor(mBgColor);
        canvas.drawArc(mOval, 0, 360, false, mPaint);

        mPaint.setShader(mShaderFirst);
        canvas.drawArc(mOval, mStartAngle, mPercentFirst * 3.6f, false, mPaint);

        mPaint.setShader(mShaderSecond);
        canvas.drawArc(mOval, mStartAngle, mPercentSecond * 3.6f, false, mPaint);

        mPaint.setShader(mShaderThird);
        canvas.drawArc(mOval, mStartAngle, mPercentThird * 3.6f, false, mPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        updateOval();
        mShaderFirst  = new LinearGradient(mOval.left, mOval.top, mOval.left, mOval.bottom, mColorFirst, mColorFirst, Shader.TileMode.MIRROR);
        mShaderSecond = new LinearGradient(mOval.left, mOval.top, mOval.left, mOval.bottom, mColorSecond, mColorSecond, Shader.TileMode.MIRROR);
        mShaderThird  = new LinearGradient(mOval.left, mOval.top, mOval.left, mOval.bottom, mColorThird, mColorThird, Shader.TileMode.MIRROR);
    }


    public void updatePercent(STATUS status,int mPercent){
        switch (status){
            case FIRST:
                mPercentFirst = mPercent;
                break;
            case SECOND:
                mPercentSecond = mPercent;
                break;
            case THIRD:
                mPercentThird  = mPercent;
                break;
        }

        refreshTheLayout();
    }


    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeWidth(float mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
        mPaint.setStrokeWidth(mStrokeWidth);
        updateOval();
        refreshTheLayout();
    }

    private void updateOval() {
        int xp = getPaddingLeft() + getPaddingRight();
        int yp = getPaddingBottom() + getPaddingTop();
        mOval = new RectF(getPaddingLeft() + mStrokeWidth, getPaddingTop() + mStrokeWidth,
                getPaddingLeft() + (getWidth() - xp) - mStrokeWidth,
                getPaddingTop() + (getHeight() - yp) - mStrokeWidth);
    }

    public void setStrokeWidthDp(float dp) {
        this.mStrokeWidth = dp2px(dp);
        mPaint.setStrokeWidth(mStrokeWidth);
        updateOval();
        refreshTheLayout();
    }

    public void refreshTheLayout() {
        invalidate();
        requestLayout();
    }




    public float getStartAngle() {
        return mStartAngle;
    }

    public void setStartAngle(float mStartAngle) {
        this.mStartAngle = mStartAngle + 270;
        refreshTheLayout();
    }

    public void animateIndeterminate() {
        animateIndeterminate(800, new AccelerateDecelerateInterpolator());
    }

    public void animateIndeterminate(int durationOneCircle,
                                     TimeInterpolator interpolator) {
        animator = ObjectAnimator.ofFloat(this, "startAngle", getStartAngle(), getStartAngle() + 360);
        if (interpolator != null) animator.setInterpolator(interpolator);
        animator.setDuration(durationOneCircle);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.start();
    }

    public void stopAnimateIndeterminate() {
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
    }
}