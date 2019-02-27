package com.pagatodo.yaganaste.ui_wallet.views;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;

public class CustomDots extends LinearLayout {

    private ImageView[] dots;

    public CustomDots(Context context) {
        super(context);
        //init();
    }

    public CustomDots(Context context, AttributeSet attrs) {
        super(context, attrs);
        //init();
    }

    public CustomDots(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //init();
    }

    public void setView(int pageCurrent, int dotsCount){
        //inflate(getContext(), R.layout.dots_layout, this);
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_selected_dot_wallet));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(11, 0, 11, 0);
            this.addView(dots[i], params);
        }

        dots[(pageCurrent) % dotsCount].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.selected_dot_wallet));
    }

    public void selectDots(int lastPosition, int nextposition) {
        dots[lastPosition].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_selected_dot_wallet));
        dots[nextposition].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.selected_dot_wallet));
    }



    /*private void setUiPageViewController() {
        //dotsCount = (pageCurrent - 1 )%cardWalletAdpater.getCount();
        dotsCount = cardWalletAdpater.getSize();
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_selected_dot_wallet));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(22, 0, 22, 0);
            pager_indicator.addView(dots[i], params);
        }
        dots[(pageCurrent) % cardWalletAdpater.getSize()].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.selected_dot_wallet));
    }*/
}
