package com.pagatodo.view_manager.pages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.view_manager.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class MovementsPageAdapater extends PagerAdapter {

    private ArrayList<String> pages;


    public MovementsPageAdapater() {
        this.pages = new ArrayList<>();
    }

    public ArrayList<String> getPages() {
        return pages;
    }

    public void setPages(ArrayList<String> pages) {
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
                .inflate(R.layout.movement_page_adapter,container,false);
        container.addView(rootView);
        return rootView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
