package com.pagatodo.yaganaste.ui_wallet.bookmarks.compositions;

import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.ui_wallet.bookmarks.builders.Component;

import java.util.ArrayList;
import java.util.List;

public class CompositeLayoutAddFavorites implements Component {

    private List<Component> components = new ArrayList<>();
    private LinearLayout linearLayout;

    public CompositeLayoutAddFavorites(LinearLayout layout, int id){
        this.linearLayout = layout;
        setContent(id);
    }

    @Override
    public void add(Component component) {
        components.add(component);
    }

    @Override
    public void setContent(int id) {
        this.linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        this.linearLayout.setOrientation(LinearLayout.VERTICAL);
        this.linearLayout.setPadding(dp(10), dp(10), dp(10), dp(10));

    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(linearLayout);
        for (Component c: components){
            c.inflate(linearLayout);
        }
    }

    private int dp(int px){
        float scale = this.linearLayout.getResources().getDisplayMetrics().density;
        return (int) (scale * px + 0.5f);
    }
}
