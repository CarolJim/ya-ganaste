package com.pagatodo.yaganaste.ui_wallet.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;

public class BoardIndicationsView extends LinearLayout {

    private Context context;

    public BoardIndicationsView(Context context) {
        super(context);
        init(context);
    }

    public BoardIndicationsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BoardIndicationsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        inflate(getContext(), R.layout.dots_layout, this);
    }

}
