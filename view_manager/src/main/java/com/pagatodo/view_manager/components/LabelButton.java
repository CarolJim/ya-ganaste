package com.pagatodo.view_manager.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.view_manager.R;

import androidx.annotation.Nullable;

public class LabelButton extends LinearLayout {

    private Button btnAll;
    private TextView label;

    public LabelButton(Context context) {
        super(context);
        init(null);
    }

    public LabelButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LabelButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void init(AttributeSet attrs){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rootView = inflater.inflate(R.layout.label_button,this,false);
        label = rootView.findViewById(R.id.label_btn);
        btnAll = rootView.findViewById(R.id.button_all);
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.LabelButton,
                    0, 0);
            try {
                String resTextTitle = a.getString(R.styleable.LabelButton_labelBtn);
                label.setText(resTextTitle);
            } finally {
                a.recycle();
            }
        }
        this.addView(rootView);
    }

    public void setOnClick(View.OnClickListener listener){
        btnAll.setOnClickListener(listener);
    }
    public void setLabel(String labeltitle){
        label.setText(labeltitle);

    }
    public void setLabelButton(String labeltitleButton){
        btnAll.setText(labeltitleButton);
    }

}
