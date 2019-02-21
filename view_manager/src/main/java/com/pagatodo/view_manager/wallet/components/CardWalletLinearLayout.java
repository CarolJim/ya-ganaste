package com.pagatodo.view_manager.wallet.components;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class CardWalletLinearLayout extends LinearLayout {

    public final static float BIG_SCALE = 1.0f;
    public final static float SMALL_SCALE = 0.6f;
    public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;

    private float scale = BIG_SCALE;

    public CardWalletLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardWalletLinearLayout(Context context) {
        super(context);
    }

    public void setScaleBoth(float scale) {
        this.scale = scale;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = this.getWidth();
        int h = this.getHeight();
        canvas.scale(scale, scale, w / 2, h / 2);
        super.onDraw(canvas);
    }
}
