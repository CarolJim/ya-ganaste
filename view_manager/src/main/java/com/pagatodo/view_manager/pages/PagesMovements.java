package com.pagatodo.view_manager.pages;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.pagatodo.view_manager.R;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class PagesMovements extends LinearLayout {

    public PagesMovements(Context context) {
        super(context);
        init();
    }

    public PagesMovements(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PagesMovements(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.movement_page,this,false);
        ViewPager pager = rootView.findViewById(R.id.view_page_mov);
        MovementsPageAdapater adapater = new MovementsPageAdapater();
        pager.setAdapter(adapater);
        this.addView(rootView);
    }
}
