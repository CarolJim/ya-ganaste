package com.pagatodo.yaganaste.ui_wallet.patterns.components;

import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.patterns.interfaces.Component;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;

import static com.pagatodo.yaganaste.utils.UtilsGraphics.Dp;

public class ButtonLeaf implements Component{

    private StyleButton button;

    public ButtonLeaf(StyleButton button, int textButtonRes) {
        this.button = button;
        setContent(textButtonRes);
    }

    @Override
    public void add(Component component) {

    }

    @Override
    public void setContent(Object item) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                Dp(214,button),
                Dp(45,button));
        params.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
        button.setLayoutParams(params);
        button.setBackgroundResource(R.drawable.button_rounded_blue);
        button.setTextColor(Color.WHITE);
        button.setGravity(Gravity.CENTER);
        button.setText((int)item);
        button.setAllCaps(false);
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX,15);




/*
        <item name="android:layout_width">@dimen/width_btns_big</item>
        <item name="android:layout_height">@dimen/height_btns</item>
        <item name="android:background">@drawable/button_rectangle_blue_selector</item>
        <item name="android:textColor">@android:color/white</item>
        <item name="android:layout_gravity">center_horizontal</item>
        <item name="android:layout_centerHorizontal">true</item>
        <item name="android:textSize">@dimen/size_text_style_2_value</item>

android:id="@+id/btnNextBussinesInfo"
            style="@style/buttonSquareRoundedBlue"
            android:background="@drawable/button_rounded_blue"
            android:gravity="center"
            android:text="@string/nextButton"
            android:textAllCaps="false"
            android:textSize="@dimen/generic_button_normal_text_size" />
 */
    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(button);
    }
}
