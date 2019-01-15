package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.pagatodo.yaganaste.utils.FontCache;


/**
 * Created by icruz on 20/02/2018.
 */

public class CustomRadioButton extends androidx.appcompat.widget.AppCompatRadioButton {

    public CustomRadioButton(Context context) {
        super(context);
        init(context);
    }

    public CustomRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context){
        Typeface customFont = FontCache.getTypeface("fonts/roboto/Roboto-Regular.ttf", context);
        setTypeface(customFont);
    }
}
