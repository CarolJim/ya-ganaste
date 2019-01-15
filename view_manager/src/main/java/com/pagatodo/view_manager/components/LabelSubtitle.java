package com.pagatodo.view_manager.components;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.dataholders.LabelDataHolder;
import com.pagatodo.view_manager.holders.LabelHolder;

public class LabelSubtitle extends LinearLayout {

    private LabelHolder holder;

    public LabelSubtitle(Context context) {
        super(context);
        init(null);
    }

    public LabelSubtitle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LabelSubtitle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rootView = inflater.inflate(R.layout.label_subtitle,this,false);
        holder = new LabelHolder(rootView);
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.LabelSubtitle,
                    0, 0);
            try {
                String resTextTitle = a.getString(R.styleable.LabelSubtitle_labelTitleSingle);
                String resTextSubtitle = a.getString(R.styleable.LabelSubtitle_labelSubtitleSingle);
                holder.bind(new LabelDataHolder(resTextTitle,resTextSubtitle),null);
            } finally {
                a.recycle();
            }
        }
        holder.inflate(this);
    }

    public void setData(String subTitle){
        holder.setTextSubtitle(subTitle);
    }
}
