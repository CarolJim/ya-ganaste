package com.pagatodo.yaganaste.ui_wallet.patterns.components;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.patterns.interfaces.Component;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import static com.pagatodo.yaganaste.ui_wallet.patterns.FormBuilder.TYPE_SUBTITLE;
import static com.pagatodo.yaganaste.ui_wallet.patterns.FormBuilder.TYPE_TITLE;
import static com.pagatodo.yaganaste.utils.UtilsGraphics.Dp;

public class TextLeaf implements Component{


    private StyleTextView textView;
    private Context context;
    private int typeText;

    public TextLeaf(StyleTextView textView, int typeText, int id){
        this.textView = textView;
        this.context = textView.getContext();
        this.typeText = typeText;
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
        if (this.typeText == TYPE_TITLE){
            textView.setLayoutParams(params);
            textView.setTextColor(this.context.getResources().getColor(R.color.colorAccent));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.context.getResources().getDimension(R.dimen.size_text_style_2_value));
        }
        if (this.typeText == TYPE_SUBTITLE){
            params.setMargins(0,8,0,32);
            textView.setLayoutParams(params);
            textView.setTextColor(this.context.getResources().getColor(R.color.adadad));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        }

        textView.setText((int) item);




        /*
        android:id="@+id/titulo_datos_usuario"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:layout_gravity="center"
                        android:layout_marginBottom="32dp"
                        android:layout_marginTop="8dp"
                        android:text="@string/sub_titulo_info_adic"
                        android:textAlignment="center"
                        android:textColor="@color/adadad"
                        android:textSize="16sp"
                        app:tipo="Text"
         */
    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(textView);
    }
}
