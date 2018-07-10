package com.pagatodo.yaganaste.ui_wallet.patterns.builders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui.account.register.adapters.BussinesLineSpinnerAdapter;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoVideoTutorials;
import com.pagatodo.yaganaste.ui_wallet.holders.ButtonSimpleViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.InputDataViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.LinearTextViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.holders.QuestionViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.SpinnerHolder;
import com.pagatodo.yaganaste.ui_wallet.patterns.components.TextLeaf;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

public class FormBuilder {

    public static final int TYPE_TITLE = 1;
    public static final int TYPE_SUBTITLE = 2;
    private Context context;

    public FormBuilder(Context context) {
        this.context = context;
    }

    public TextLeaf setTitle(int resText){
        return new TextLeaf(new StyleTextView(context),TYPE_TITLE,resText);
    }

    public QuestionViewHolder setQuest(ViewGroup parent,int text, boolean deafultR, RadioGroup.OnCheckedChangeListener listener){
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = inflater.inflate(R.layout.cuestion_layout, parent, false);
        QuestionViewHolder questionViewHolder = new QuestionViewHolder(layout,listener);
        questionViewHolder.bind(new QuestionViewHolder.Question(text,deafultR),null);
        return questionViewHolder;
    }

    public SpinnerHolder setSpinner(ViewGroup parent, int texthint, BussinesLineSpinnerAdapter adapter, OnClickItemHolderListener listener){
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = inflater.inflate(R.layout.spinner_view_layout, parent, false);
        SpinnerHolder spinnerHolder = new SpinnerHolder(layout);
        spinnerHolder.bind(new SpinnerHolder.SpinerItem(texthint,adapter),listener);
        return spinnerHolder;
    }

    public InputDataViewHolder setInputText(ViewGroup parent,String texthint){
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = inflater.inflate(R.layout.textinput_view_layout, parent, false);
        InputDataViewHolder inputDataViewHolder = new InputDataViewHolder(layout);
        inputDataViewHolder.bind(texthint,null);
        return inputDataViewHolder;
    }

    public View setSpace(int high){
        View space = new View(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                high);
        space.setLayoutParams(params);
        return space;
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
