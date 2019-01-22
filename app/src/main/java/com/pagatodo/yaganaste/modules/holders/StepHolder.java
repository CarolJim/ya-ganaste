package com.pagatodo.yaganaste.modules.holders;

import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.components.stepbar.StepBarItem;
import com.pagatodo.yaganaste.ui_wallet.holders.GenericHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;

public class StepHolder extends GenericHolder {

    private LinearLayout stepState;
    private LinearLayout check;
    private LinearLayout stepActive;
    private TextView numText;
    private TextView textNumCheck;
    private TextView textNumActive;
    private AnimatedVectorDrawable drawable;


    public StepHolder(View itemView) {
        super(itemView);
        init();
    }

    @Override
    public void init() {
        this.stepState = this.itemView.findViewById(R.id.step_inactive);
        this.numText = this.itemView.findViewById(R.id.text_num);
        this.check = this.itemView.findViewById(R.id.step_check);
        this.stepActive = this.itemView.findViewById(R.id.step_active);
        this.textNumCheck = this.itemView.findViewById(R.id.text_num_check);
        this.textNumActive = this.itemView.findViewById(R.id.text_num_active);
    }

    @Override
    public void bind(Object item, OnClickItemHolderListener listener) {
        if (item != null){
            StepBarItem stepBarItem = (StepBarItem) item;
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable = (AnimatedVectorDrawable) stepBarItem.getResource();
                this.check.setImageDrawable(drawable);
            } else {*/
            this.textNumCheck.setText(stepBarItem.getNumText());
            this.numText.setText(stepBarItem.getNumText());
            this.textNumActive.setText(stepBarItem.getNumText());
                switch (stepBarItem.getState()) {
                    case inactive:
                        this.stepState.setVisibility(View.VISIBLE);
                        this.stepState.setBackgroundResource(R.drawable.bck_step_inactive);
                        this.numText.setText(stepBarItem.getNumText());
                        this.numText.setTextColor(Color.parseColor("#9e9e9e"));
                        this.check.setVisibility(View.GONE);
                        break;
                    case active:

                        this.stepState.setVisibility(View.GONE);
                        //this.stepState.setBackgroundResource(R.drawable.bck_step_active);
                        this.stepActive.setVisibility(View.VISIBLE);
                        /*float scalingFactor = 0.5f; // scale down to half the size
                        this.stepActive.setScaleX(scalingFactor);
                        this.stepActive.setScaleY(scalingFactor);*/
                        this.numText.setTextColor(Color.parseColor("#FFFFFF"));
                        this.textNumActive.setTextColor(Color.parseColor("#FFFFFF"));
                        this.check.setVisibility(View.GONE);
                        break;
                    case check:
                        this.check.setVisibility(View.VISIBLE);
                        this.stepState.setVisibility(View.GONE);
                        break;
                }
            }
        //}
    }

    public void setStepState(StepBarItem.State state){
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Animatable)(this.check).getDrawable()).start();
            this.check.setVisibility(View.VISIBLE);
            this.stepState.setVisibility(View.GONE);

        } else {*/
            switch (state) {
                case inactive:
                    this.stepState.setVisibility(View.VISIBLE);
                    this.stepState.setBackgroundResource(R.drawable.bck_step_inactive);
                    this.numText.setTextColor(Color.parseColor("#9e9e9e"));
                    this.check.setVisibility(View.GONE);
                    this.stepActive.setVisibility(View.GONE);
                    break;
                case active:
                    /*this.stepState.setVisibility(View.VISIBLE);
                    this.stepState.setBackgroundResource(R.drawable.bck_step_active);
                    this.numText.setTextColor(Color.parseColor("#FFFFFF"));
                    this.check.setVisibility(View.GONE);*/
                    this.stepState.setVisibility(View.GONE);
                    //this.stepState.setBackgroundResource(R.drawable.bck_step_active);
                    this.stepActive.setVisibility(View.VISIBLE);
                    /*float scalingFactor = 0.8f; // scale down to half the size
                    this.stepActive.setScaleX(scalingFactor);
                    this.stepActive.setScaleY(scalingFactor);*/
                    this.numText.setTextColor(Color.parseColor("#FFFFFF"));
                    textNumActive.setTextColor(Color.parseColor("#FFFFFF"));
                    this.check.setVisibility(View.GONE);
                    break;
                case check:
                    this.check.setVisibility(View.VISIBLE);
                    this.stepState.setVisibility(View.GONE);
                    this.stepActive.setVisibility(View.GONE);
                    break;
            }
       // }
    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(this.itemView);
    }

    @Override
    public View getView() {
        return this.itemView;
    }
}
