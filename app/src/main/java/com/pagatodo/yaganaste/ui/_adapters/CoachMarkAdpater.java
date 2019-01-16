package com.pagatodo.yaganaste.ui._adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pagatodo.yaganaste.R;

import java.util.ArrayList;

/**
 * Created by icruz on 06/12/2017.
 */

public class CoachMarkAdpater extends PagerAdapter {
    private Context mContext;
    private int[]onBoardItems;


    public CoachMarkAdpater(Context mContext, int[] items) {
        this.mContext = mContext;
        this.onBoardItems = items;
    }

    @Override
    public int getCount() {
        return onBoardItems.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup itemView = (ViewGroup) inflater.inflate(R.layout.coachmarks_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.iv_onboard);
        imageView.setImageResource(onBoardItems[position]);

        container.addView(itemView);

        return itemView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }
}
