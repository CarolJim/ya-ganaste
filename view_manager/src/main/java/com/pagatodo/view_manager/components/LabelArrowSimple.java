package com.pagatodo.view_manager.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.dataholders.LabelDataHolder;
import com.pagatodo.view_manager.holders.LabelSimpleHolder;

import androidx.annotation.Nullable;

public class LabelArrowSimple extends LinearLayout {

    public LabelArrowSimple(Context context) {
        super(context);
        init(null);
    }

    public LabelArrowSimple(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LabelArrowSimple(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rootView = inflater.inflate(R.layout.label_simple_arrow,this,false);
        LabelSimpleHolder holder = new LabelSimpleHolder(rootView);

        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.LabelArrowSimple,
                    0, 0);
            try {
                String resTextTitle = a.getString(R.styleable.LabelArrowSimple_labelTitle);
                holder.bind(new LabelDataHolder(resTextTitle,""),null);
            } finally {
                a.recycle();
            }
        }
        holder.inflate(this);

    }
}
