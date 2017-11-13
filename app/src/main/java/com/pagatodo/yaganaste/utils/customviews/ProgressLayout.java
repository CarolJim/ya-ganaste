package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;

import butterknife.ButterKnife;

/**
 * Created by flima on 24/03/2017.
 */

public class ProgressLayout extends LinearLayout implements View.OnClickListener {

    private TextView txtMessage;
    private FrameLayout frameProgresGif;

    public ProgressLayout(Context context) {
        super(context);
        init();
    }

    public ProgressLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        li.inflate(R.layout.progress_layout, this, true);
        txtMessage = ButterKnife.findById(this, R.id.txtMessage);
        frameProgresGif = ButterKnife.findById(this, R.id.frameProgresGif);
        setBackgroundResource(R.color.bg_progress);
        setOnClickListener(this);
    }

    public void setTextMessage(String message) {
        txtMessage.setText(message);
    }

    /*public void setBackgroundColor(@DrawableRes int color) {
        setBackgroundResource(getResources().getColor(color));
    }*/

    public void setVisivilityImage(int visibility){
        frameProgresGif.setVisibility(visibility);
    }


    @Override
    public void onClick(View v) {
        //No action to perform
    }
}
