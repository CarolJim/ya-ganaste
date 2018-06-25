package com.pagatodo.yaganaste.ui_wallet.patterns.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.patterns.interfaces.Component;

public class SpinnerLeaf implements Component {

    private LinearLayout container;
    private Context context;
    private Spinner spinner;
    private View itemView;
    private LinearLayout layout;

    public SpinnerLeaf(Context context) {
        this.context = context;
        setContent(null);
    }

    @Override
    public void add(Component component) {

    }

    @Override
    public void setContent(Object item) {
        LayoutInflater inflater = LayoutInflater.from(context);
        layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        );
        itemView = inflater.inflate(R.layout.spinner_view_layout, layout, false);

    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(itemView);
    }
}
