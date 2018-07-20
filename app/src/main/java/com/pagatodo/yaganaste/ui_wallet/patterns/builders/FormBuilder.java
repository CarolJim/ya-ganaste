package com.pagatodo.yaganaste.ui_wallet.patterns.builders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoVideoTutorials;
import com.pagatodo.yaganaste.ui_wallet.holders.ButtonSimpleViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.LinearTextViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;


public class FormBuilder {

    private Context context;

    public FormBuilder(Context context) {
        this.context = context;
    }

    public View setSpaceVertical(int high){
        View space = new View(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                high,
                high);
        space.setLayoutParams(params);
        return space;
    }

    @SuppressLint("ResourceType")
    public View setViewLine(){
        View space = new View(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                1);
        space.setLayoutParams(params);
        space.setBackgroundResource(R.color.gray_text_wallet_4);
        return space;
    }


    public LinearTextViewHolder setLineraText(ViewGroup parent,
                                              DtoVideoTutorials item,
                                              OnClickItemHolderListener listener){
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = inflater.inflate(R.layout.linear_text_layout, parent, false);
        LinearTextViewHolder linearTextViewHolder = new LinearTextViewHolder(layout);
        linearTextViewHolder.bind(item,listener);
        return linearTextViewHolder;
    }

    public ButtonSimpleViewHolder setButton(ViewGroup parent, ElementView elementView, OnClickItemHolderListener listener){
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = inflater.inflate(R.layout.view_element, parent, false);
        ButtonSimpleViewHolder buttonSimpleViewHolder = new ButtonSimpleViewHolder(layout);
        buttonSimpleViewHolder.bind(elementView,listener);
        return buttonSimpleViewHolder;
    }


}
