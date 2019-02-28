package com.pagatodo.view_manager.pages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.dataholders.MovementDataHolder;
import com.pagatodo.view_manager.recyclers.MovementsRecycler;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class MovementsPageAdapater extends PagerAdapter {

    private ArrayList<MovData> pages;


    MovementsPageAdapater() {
        this.pages = new ArrayList<>();
    }

    void setPages(ArrayList<MovData> pages) {
        this.pages = pages;
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View rootView = LayoutInflater.from(container.getContext())
                .inflate(R.layout.movement_page_adapter, container, false);
        MovementsRecycler movementsRecycler = rootView.findViewById(R.id.rec_movement);
        movementsRecycler.setItems(pages.get(position).getMovementDataHolders());
        container.addView(rootView);
        return rootView;
    }


    void setItems(ArrayList<MovementDataHolder> itemlist, int position){
        this.pages.get(position).setMovementDataHolders(itemlist);
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.pages.get(position).getNameTab();
    }
}
