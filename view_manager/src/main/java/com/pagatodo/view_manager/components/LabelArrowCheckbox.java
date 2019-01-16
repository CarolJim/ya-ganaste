package com.pagatodo.view_manager.components;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.dataholders.LabelArrowCheckboxDataHolder;
import com.pagatodo.view_manager.holders.LabelArrowCheckHolder;

public class LabelArrowCheckbox extends LinearLayout {
    public LabelArrowCheckbox(Context context) {
        super(context);
        init(null);
    }

    public LabelArrowCheckbox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LabelArrowCheckbox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rootView=inflater.inflate(R.layout.checkbox,this,false);
        LabelArrowCheckHolder holder = new LabelArrowCheckHolder(rootView);
        if (attrs!=null){
            TypedArray a =getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.LabelArrow,0,0
            );
            try {
                String resTextTitle=a.getString(R.styleable.LabelArrow_lablelTitle);
                String resTextSubtitle=a.getString(R.styleable.LabelArrow_labelSubtilte);
                Boolean resChecked=a.getBoolean(R.styleable.LabelArrow_checked,true);
                holder.bind(new LabelArrowCheckboxDataHolder(resTextTitle,resTextSubtitle,resChecked),null);
            }finally {
                a.recycle();
            }
        }
        holder.inflate(this);
    }
}
