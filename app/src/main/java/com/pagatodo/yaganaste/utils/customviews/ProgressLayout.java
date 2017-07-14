package com.pagatodo.yaganaste.utils.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;

import butterknife.ButterKnife;

/**
 * Created by flima on 24/03/2017.
 */

public class ProgressLayout extends LinearLayout implements View.OnClickListener {

    private TextView txtMessage;

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
        setBackgroundResource(R.color.bg_progress);
        setOnClickListener(this);
    }

    public void setTextMessage(String message) {
        txtMessage.setText(message);
    }

    @Override
    public void onClick(View v) {
        //No action to perform
    }
}
