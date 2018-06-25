package com.pagatodo.yaganaste.ui_wallet.patterns.components;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.patterns.interfaces.Component;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.List;

public class BusinessDataComposite implements Component {

    private List<Component> components = new ArrayList<>();
    private StyleTextView textView;

    public BusinessDataComposite(StyleTextView textView, int resText) {
        this.textView = textView;
        setContent(resText);
    }

    @Override
    public void add(Component component) {
        components.add(component);
    }

    @Override
    public void setContent(Object item) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText((int) item);
        textView.setTextColor(textView.getContext().getResources().getColor(R.color.colorAccent));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textView.getContext().getResources().getDimension(R.dimen.size_text_style_2_value));

    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(textView);
        for (Component c : components){
            c.inflate(layout);
        }
    }
}
