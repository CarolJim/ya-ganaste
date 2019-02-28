package com.pagatodo.view_manager.pages;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.dataholders.MovementDataHolder;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class PagesMovements extends LinearLayout implements ViewPager.OnPageChangeListener {

    private MovementsPageAdapater adapater;
    private TabLayout tabsMonths;
    private ViewPager pager;
    private OnPageListener listener;

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
        pager = rootView.findViewById(R.id.view_page_mov);
        tabsMonths = rootView.findViewById(R.id.tabs_months);
        adapater = new MovementsPageAdapater();
        pager.setAdapter(adapater);
        tabsMonths.setupWithViewPager(pager);
        ArrayList<MovData> listname = new ArrayList<>();
        /*ArrayList<MovementDataHolder> itemlist = new ArrayList<>();

        itemlist.add(MovementDataHolder.create("26","Ene","Venta con tarjeta",
                "Santader 5896","150",
                getContext().getResources().getDrawable(R.drawable.ic_dove),null));

        itemlist.add(MovementDataHolder.create("26","Ene","Venta con tarjeta",
                "Santader 5896","150",
                getContext().getResources().getDrawable(R.drawable.ic_dove),null));
        listname.add(new MovData("Ene"));
        listname.add(new MovData("Feb"));*/
        //adapater.setPages(listname);
        //this.pager.addOnPageChangeListener(this);
        this.addView(rootView);
    }

    public void setPage(ArrayList<MovData> listname){
        this.adapater.setPages(listname);
        this.adapater.notifyDataSetChanged();

    }

    public void setItems(ArrayList<MovementDataHolder> itemlist, int position){
        this.adapater.setItems(itemlist,position);
        this.adapater.notifyDataSetChanged();
    }

    public void setOnClickTabs(ViewPager.OnPageChangeListener listener){
        this.pager.addOnPageChangeListener(listener);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (this.listener != null){
            this.listener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public interface OnPageListener {
        void onPageSelected(int position);
    }
}
