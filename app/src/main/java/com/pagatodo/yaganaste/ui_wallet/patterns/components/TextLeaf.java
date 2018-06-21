package com.pagatodo.yaganaste.ui_wallet.patterns.components;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.patterns.interfaces.Component;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import static com.pagatodo.yaganaste.utils.UtilsGraphics.Dp;

public class TextLeaf implements Component{

    private StyleTextView textView;
    private Context context;

    public TextLeaf(StyleTextView textView, int id){
        this.textView = textView;
        this.context = textView.getContext();
        setContent(id);
    }

    @Override
    public void add(Component component) {

    }

    @Override
    public void setContent(Object item) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        textView.setLayoutParams(params);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText((int) item);
        textView.setTextColor(this.context.getResources().getColor(R.color.colorAccent));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.context.getResources().getDimension(R.dimen.size_text_style_2_value));

        /*
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:layout_marginTop="27dp"
        android:gravity="center_horizontal"
        android:text="@string/bussinesInfoTitle"
        android:textColor="@color/colorAccent"
        android:textSize="@dimen/size_text_style_2_value"
         */
    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(textView);
    }
}
