package com.pagatodo.yaganaste.modules.components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

public class FeetQrDetail extends LinearLayout {

    private StyleTextView one;
    private StyleTextView two;
    private StyleTextView three;

    public FeetQrDetail(Context context) {
        super(context);
        init();
    }

    public FeetQrDetail(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FeetQrDetail(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rootView = inflater.inflate(R.layout.feet_qr_detail,this,false);
        one = rootView.findViewById(R.id.small_one);
        two = rootView.findViewById(R.id.small_two);
        three = rootView.findViewById(R.id.big);
        this.addView(rootView);
    }

    @SuppressLint("SetTextI18n")
    public void setPlate(String plate){
        if (plate.length() == 8){
            one.setText(plate.substring(0,3));
            two.setText(plate.substring(4,7));
            three.setText(plate.substring(8));
        } else {
            one.setText("0000");
            two.setText("0000");
            three.setText("0000");
        }
    }
}
