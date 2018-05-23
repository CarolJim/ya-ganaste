package com.pagatodo.yaganaste.ui_wallet.bookmarks.compositions;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.bookmarks.builders.Component;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

public class TextNode implements Component{

    private StyleTextView textView;
    public Context context;

    public TextNode(Context context, StyleTextView textView, int id) {
        this.context = context;
        this.textView = textView;
        setContent(id);
    }

    @Override
    public void add(Component component) {

    }

    @Override
    public void setContent(int id) {
        textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText(id);
        textView.setTextColor(context.getResources().getColor(R.color.colorAccent));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(textView);
    }

}
