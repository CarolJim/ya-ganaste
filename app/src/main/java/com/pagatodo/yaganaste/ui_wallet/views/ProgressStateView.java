package com.pagatodo.yaganaste.ui_wallet.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ProgressStateView extends View implements ProgressStates{

    private int sizeStep;
    private int resDisabled;
    private int resEnabled;
    private int resChecked;

    public ProgressStateView(Context context) {
        super(context);
        init();
    }

    public ProgressStateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressStateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        sizeStep = 5;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dp(14),dp(14));
        this.setLayoutParams(params);

    }

    private Drawable createDrawable(String resColor){
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setColor(Color.parseColor(resColor));
        //shape.setSize(dp(this),dp(this));
        return shape;
    }

    private int dp(int px){
        float scale = this.getResources().getDisplayMetrics().density;
        return (int) (scale * px + 0.5f);
    }

    @Override
    public void check(boolean check) {

    }

    @Override
    public void stepNext() {

    }

    @Override
    public void stepBack() {

    }
}
