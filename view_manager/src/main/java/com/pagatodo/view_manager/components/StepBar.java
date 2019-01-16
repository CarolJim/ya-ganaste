package com.pagatodo.view_manager.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.pagatodo.view_manager.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StepBar extends FrameLayout {
    public StepBar(@NonNull Context context) {
        super(context);
        init();
    }

    public StepBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StepBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.step_bar,this,false);
        Step stepOne = view.findViewById(R.id.step_one);
        stepOne.active("1");
        addView(view);
    }
}
