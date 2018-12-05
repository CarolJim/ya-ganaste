package com.pagatodo.yaganaste.modules.components.stepbar;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.ui_wallet.bookmarks.builders.Component;

public class LineSeparation implements Component {

    private LinearLayout linearLayout;

    public LineSeparation(Context context) {
        linearLayout = new LinearLayout(context);
        setContent(0);
    }

    @Override
    public void add(Component component) {

    }

    @Override
    public void setContent(int id) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dp(24), dp(2));
        linearLayout.setLayoutParams(params);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setBackgroundColor(Color.parseColor("#00a1e1"));
    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(linearLayout);
    }

    private int dp(int px){
        float scale = linearLayout.getResources().getDisplayMetrics().density;
        return (int) (scale * px + 0.5f);
    }
}
