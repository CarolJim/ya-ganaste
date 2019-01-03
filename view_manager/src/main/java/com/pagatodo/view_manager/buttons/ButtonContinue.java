package com.pagatodo.view_manager.buttons;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.pagatodo.view_manager.R;

public class ButtonContinue extends AppCompatButton {

    public ButtonContinue(Context context) {
        super(context);
        init();
    }

    public ButtonContinue(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ButtonContinue(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        this.inactive();
        this.setTextColor(Color.parseColor("#FFFFFF"));
        this.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
    }

    public void active(){
        this.setBackgroundResource(R.drawable.backgraund_button_active);
        this.setEnabled(true);
    }

    public void inactive(){
        this.setBackgroundResource(R.drawable.backgraund_button_inactive);
        this.setEnabled(false);
    }
}
