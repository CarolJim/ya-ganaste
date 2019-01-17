package com.pagatodo.view_manager.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.LauncherHolder;
import com.pagatodo.view_manager.controllers.OnHolderListener;

import androidx.annotation.Nullable;

public class Step extends FrameLayout implements LauncherHolder<String> {

    private View rootView;
    private TextView numText;
    private LayoutInflater inflater;

    public Step(Context context) {
        super(context);
        init();
    }

    public Step(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Step(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        inflater = LayoutInflater.from(getContext());
        rootView = inflater.inflate(R.layout.step_desactive,this,false);
        addView(rootView);
    }

    public void active(String num){
        rootView = inflater.inflate(R.layout.step_active,this,false);
        numText = rootView.findViewById(R.id.num_text);
        bind(num,null);
    }

    @Override
    public void bind(String item, OnHolderListener<String> listener) {
        numText.setText(item);
    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(rootView);
    }

    @Override
    public View getView() {
        return rootView;
    }
}
