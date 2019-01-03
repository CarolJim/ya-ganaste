package com.pagatodo.view_manager.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.dataholders.LabelArrowDataHolder;
import com.pagatodo.view_manager.holders.LabelArrowHolder;

public class LabelArrow extends LinearLayout {
    public LabelArrow(Context context) {
        super(context);
        init(null);
    }

    public LabelArrow(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LabelArrow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rootView = inflater.inflate(R.layout.label_arrow,this,false);
        LabelArrowHolder holder = new LabelArrowHolder(rootView);
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.LabelArrow,
                    0, 0);
            try {
                String resTextTitle = a.getString(R.styleable.LabelArrow_lablelTitle);
                String resTextSubtitle = a.getString(R.styleable.LabelArrow_labelSubtilte);
                holder.bind(new LabelArrowDataHolder(resTextTitle,resTextSubtitle),null);
            } finally {
                a.recycle();
            }
        }
        holder.inflate(this);
    }
}
