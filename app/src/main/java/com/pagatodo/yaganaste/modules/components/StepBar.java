package com.pagatodo.yaganaste.modules.components;

import android.content.Context;
import android.os.Build;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.components.stepbar.LineSeparation;
import com.pagatodo.yaganaste.modules.components.stepbar.StepBarItem;
import com.pagatodo.yaganaste.modules.holders.StepHolder;

import java.util.ArrayList;

public class StepBar extends LinearLayout {

    public ArrayList<StepHolder> stepHolders;
    private int sizeSteps;
    private int currentSteps;

    public StepBar(Context context) {
        super(context);
        init();
    }

    public StepBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StepBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        stepHolders = new ArrayList<>();
        this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setGravity(Gravity.CENTER);
        this.removeAllViews();
        currentSteps = 0;
        sizeSteps = 6;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (int i = 0; i < sizeSteps; i++) {
            View view = inflater.inflate(R.layout.step_item, this, false);
            StepHolder holder = new StepHolder(view);
            holder.bind(new StepBarItem(StepBarItem.State.inactive, String.valueOf(i + 1)), null);
            holder.inflate(this);
            stepHolders.add(holder);
            if (i < sizeSteps - 1) {
                LineSeparation lineSeparation = new LineSeparation(getContext());
                lineSeparation.inflate(this);
            }
        }
        stepHolders.get(0).setStepState(StepBarItem.State.active);

    }


    private void init21(){
        /*LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.step_item, this, false);
        StepHolder holder = new StepHolder(view);
        holder.bind(new StepBarItem(StepBarItem.State.inactive, String.valueOf(0),
                getContext().getResources().getDrawable(R.drawable.step_one_animation)), null);
        holder.inflate(this);
        stepHolders.add(holder);
        LineSeparation lineSeparation = new LineSeparation(getContext());
        lineSeparation.inflate(this);*/
        createHolder(R.drawable.step_one_animation,true);
        createHolder(R.drawable.step_two_animator,true);
        createHolder(R.drawable.step_three_animator,true);
        createHolder(R.drawable.step_one_animation,true);
        createHolder(R.drawable.step_one_animation,true);
        createHolder(R.drawable.step_one_animation,false);

    }

    private void createHolder(int res, boolean line){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.step_item, this, false);
        StepHolder holder = new StepHolder(view);
        holder.bind(new StepBarItem(StepBarItem.State.inactive, String.valueOf(0),
                getContext().getResources().getDrawable(res)), null);
        holder.inflate(this);
        stepHolders.add(holder);
        if (line) {
            LineSeparation lineSeparation = new LineSeparation(getContext());
            lineSeparation.inflate(this);
        }
    }

    public void nextStep(){
        //currentSteps++;
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (currentSteps < sizeSteps - 1) {
                if (currentSteps > 0) {
                    stepHolders.get(currentSteps - 1).setStepState(StepBarItem.State.active);
                }
                stepHolders.get(currentSteps).setStepState(StepBarItem.State.active);
            }
        } else {*/
            if (currentSteps < sizeSteps - 1) {
                currentSteps++;
                stepHolders.get(currentSteps).setStepState(StepBarItem.State.active);
                if (currentSteps > 0) {
                    stepHolders.get(currentSteps - 1).setStepState(StepBarItem.State.check);
                }
            }
        //}

    }

    public void backStep(){
        if (currentSteps > 0) {
            stepHolders.get(currentSteps).setStepState(StepBarItem.State.inactive);
            currentSteps--;
            stepHolders.get(currentSteps).setStepState(StepBarItem.State.active);
        }
    }



}
