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
import android.view.animation.LinearInterpolator;

import com.pagatodo.yaganaste.R;

/**
 * Created by Dell on 25/07/2017.
 */

public class StatusViewCupo extends View {

    public static enum STATUS{
        FIRST,SECOND,ERROR
    }

    private float mPercentFirst  = 0;
    private float mPercentSecond = 0;

    private float mStrokeWidth;
    private int mBgColor = 0xffe1e1e1;
    private float mStartAngle = 0;

    private int mColorFirst    = 0;
    private int mColorSecond   = 0;
    private int mColorError    = 0;


    private Context mContext;
    private RectF mOval;
    private Paint mPaint;


    private LinearGradient mShaderFirst;
    private LinearGradient mShaderSecond;


    private ObjectAnimator animator;

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
            mColorError  = a.getColor(R.styleable.StatusCupoColors_errorColor, 0xffff4800);


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



    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        updateOval();
        mShaderFirst  = new LinearGradient(mOval.left, mOval.top, mOval.left, mOval.bottom, mColorFirst, mColorFirst, Shader.TileMode.MIRROR);
        mShaderSecond = new LinearGradient(mOval.left, mOval.top, mOval.left, mOval.bottom, mColorSecond, mColorSecond, Shader.TileMode.MIRROR);

    }


    public void updateStatus(int mPercentFirst, int mPercentSecond){
        updatePercent(STATUS.FIRST, mPercentFirst);
        animateFistColor();
        updatePercent(STATUS.SECOND,mPercentSecond);
        animateSecondColor();
    }

    public void updateError(int mPercentError,int mPercentSecond){
        updatePercent(STATUS.ERROR, mPercentError);
        animateFistColor();
        updatePercent(STATUS.SECOND,mPercentSecond);
        animateSecondColor();
    }

    private void updatePercent(STATUS status,int mPercent){
        switch (status){
            case FIRST:
                mPercentFirst = mPercent;
                mShaderFirst   = new LinearGradient(mOval.left, mOval.top, mOval.left, mOval.bottom, mColorFirst, mColorFirst, Shader.TileMode.MIRROR);
                break;
            case SECOND:
                mPercentSecond = mPercent;
                break;
            case ERROR:
                mPercentFirst  = mPercent;
                mShaderFirst   = new LinearGradient(mOval.left, mOval.top, mOval.left, mOval.bottom, mColorError, mColorError, Shader.TileMode.MIRROR);
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


    public void animateIndeterminate() {
        animateIndeterminate(1000, new AccelerateDecelerateInterpolator());
    }

    public void animateIndeterminate(int durationOneCircle,
                                     TimeInterpolator interpolator) {
        animator = ObjectAnimator.ofFloat(this, "startAngle", mStartAngle, mStartAngle + 360);
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

    public float getStartAngle() {
        return mStartAngle;
    }

    public void setStartAngle(float mStartAngle) {
        this.mStartAngle = mStartAngle + 270;
        refreshTheLayout();
    }


    public float getFirstPercent() {
        return mPercentFirst;
    }

    public void setFirstPercent(float mPercentFirst) {
        this.mPercentFirst = mPercentFirst;
        refreshTheLayout();
    }

    public float getSecondPercent() {
        return mPercentSecond;
    }

    public void setSecondPercent(float mPercentFirst) {
        this.mPercentSecond = mPercentFirst;
        refreshTheLayout();
    }

    public void animateFistColor(){
        ObjectAnimator anim = ObjectAnimator.ofFloat(this, "firstPercent",
                0, this.getFirstPercent());
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(1000);
        anim.start();
    }

    public void animateSecondColor(){
        ObjectAnimator anim = ObjectAnimator.ofFloat(this, "secondPercent",
                0, this.getSecondPercent());
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(1000);
        anim.start();
    }

}