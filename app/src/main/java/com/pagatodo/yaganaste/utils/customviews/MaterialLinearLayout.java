package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.Utils;


/**
 * @author Juan Guerra on 16/11/2016.
 * @version 1.1
 */

public class MaterialLinearLayout extends LinearLayout implements View.OnClickListener{

    private static final int VERSION = Build.VERSION.SDK_INT;
    private OnClickListener mClickListener;

    View view;
    public MaterialLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

        TypedArray configurationParams =
                context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaterialLinearLayout, 0, 0);

        try {

            TypedValue tp = new TypedValue();
            getContext().getTheme().resolveAttribute(R.attr.colorControlHighlight, tp, true);

            int selectedColor = configurationParams.getColor(R.styleable.MaterialLinearLayout_buttonRippleColor,
                    tp.data/*ContextCompat.getColor(getContext(), R.color.gris_bar_col)*/);

            int cornerRadius = configurationParams.getDimensionPixelOffset(R.styleable.MaterialLinearLayout_cornerRadius,
                    0);

            int buttonStrokeColor = configurationParams.getColor(R.styleable.MaterialLinearLayout_buttonStrokeColor,
                    ContextCompat.getColor(getContext(), R.color.colorAccent));

            int selectedStrokeColor = configurationParams.getColor(R.styleable.MaterialLinearLayout_selectedStrokeColor,
                    selectedColor);

            Drawable normalBackGround = configurationParams.getDrawable(R.styleable.MaterialLinearLayout_normalBackGround);



            if (VERSION < Build.VERSION_CODES.LOLLIPOP) {
               changeRippleColor(selectedColor, cornerRadius, buttonStrokeColor);
            } else {
                changeRippleColorMaterial(selectedColor, cornerRadius, buttonStrokeColor, selectedStrokeColor, normalBackGround);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            configurationParams.recycle();
        }
    }

    private void changeRippleColor(@ColorInt int colorSelected,
                                   int cornerRadius, @ColorInt int strokeColor) {

        GradientDrawable normalBackground = new GradientDrawable();
        normalBackground.setShape(GradientDrawable.RECTANGLE);
        normalBackground.setCornerRadius(cornerRadius);
        normalBackground.setStroke((int)Utils.convertDpToPixels(2), strokeColor);
        normalBackground.setAlpha(128);

        GradientDrawable pressedDrawable = new GradientDrawable();
        pressedDrawable.setShape(GradientDrawable.RECTANGLE);
        pressedDrawable.setCornerRadius(cornerRadius);
        pressedDrawable.setColor(colorSelected);
        pressedDrawable.setStroke((int)Utils.convertDpToPixels(2), strokeColor);

        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        states.addState(new int[]{}, normalBackground);
        setBackground(states);
    }

    private void changeRippleColorMaterial(@ColorInt int colorSelected,
                                           int cornerRadius, @ColorInt int strokeColor, @ColorInt int selectedStrokeColor, Drawable normalDrawable) throws Exception{

        GradientDrawable pressedDrawable = new GradientDrawable();
        pressedDrawable.setShape(GradientDrawable.RECTANGLE);
        pressedDrawable.setCornerRadius(cornerRadius);
        pressedDrawable.setColor(ContextCompat.getColor(getContext(), R.color.redcolor));
        pressedDrawable.setStroke((int) Utils.convertDpToPixels(2), selectedStrokeColor);


        GradientDrawable normalBackground = new GradientDrawable();
        normalBackground.setShape(GradientDrawable.RECTANGLE);
        normalBackground.setCornerRadius(cornerRadius);
        normalBackground.setStroke((int)Utils.convertDpToPixels(2), strokeColor);

        RippleDrawable rippleDrawable = new RippleDrawable(ColorStateList.valueOf(colorSelected),
                normalDrawable != null ? normalDrawable : normalBackground,pressedDrawable);

        setBackground(rippleDrawable);
    }

    private void init(){
        super.setOnClickListener(this);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        this.mClickListener = l;
    }

    @Override
    public void onClick(View v) {
        if (mClickListener != null){
            mClickListener.onClick(this);
        }
    }

}
