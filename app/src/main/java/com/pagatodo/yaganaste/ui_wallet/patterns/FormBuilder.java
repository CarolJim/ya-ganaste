package com.pagatodo.yaganaste.ui_wallet.patterns;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui.account.register.adapters.EnumSpinnerAdapter;
import com.pagatodo.yaganaste.ui.account.register.adapters.StatesSpinnerAdapter;
import com.pagatodo.yaganaste.ui_wallet.holders.InputDataViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.QuestionViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.SpinnerHolder;
import com.pagatodo.yaganaste.ui_wallet.patterns.components.AdditionalInformationComposite;
import com.pagatodo.yaganaste.ui_wallet.patterns.components.BusinessDataComposite;
import com.pagatodo.yaganaste.ui_wallet.patterns.components.ButtonLeaf;
import com.pagatodo.yaganaste.ui_wallet.patterns.components.CuestionLeaf;
import com.pagatodo.yaganaste.ui_wallet.patterns.components.InputTextLeaf;
import com.pagatodo.yaganaste.ui_wallet.patterns.components.SpinnerLeaf;
import com.pagatodo.yaganaste.ui_wallet.patterns.components.TextLeaf;
import com.pagatodo.yaganaste.ui_wallet.patterns.interfaces.Component;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

public class FormBuilder {

    public static final int TYPE_TITLE = 1;
    public static final int TYPE_SUBTITLE = 2;
    private Context context;

    public  FormBuilder(Context context) {
        this.context = context;
    }

    public TextLeaf setTitle(int resText){
        return new TextLeaf(new StyleTextView(context),TYPE_TITLE,resText);
    }
    public TextLeaf setSubTitle(int resText){
        return new TextLeaf(new StyleTextView(context),TYPE_SUBTITLE,resText);
    }

    public SpinnerLeaf setSpinner() {
        return new SpinnerLeaf(this.context);
    }



    public ButtonLeaf setButtonLeaf(int resText){
        return new ButtonLeaf(new StyleButton(context),resText);
    }

    public CuestionLeaf setQuestion(int resText, boolean check, RadioGroup.OnCheckedChangeListener listener) { return new CuestionLeaf(this.context,resText,check, listener);}

    public Component infoAditionalCuestion(RadioGroup.OnCheckedChangeListener listener){
        Component c = new AdditionalInformationComposite();
        /*
        builder.setTitle(R.string.title_informacion_adicional).inflate(layout);
        builder.setTitle(R.string.sub_titulo_info_adic).inflate(layout);
        builder.setQuestion(R.string.publicServantQuestion,false).inflate(layout);
        builder.setQuestion(R.string.mexicanQuestion,true).inflate(layout);
         */
        c.add(setTitle(R.string.title_informacion_adicional));
        c.add(setSubTitle(R.string.sub_titulo_info_adic));
        c.add(setQuestion(R.string.publicServantQuestion,false,listener));
        c.add(setQuestion(R.string.mexicanQuestion,true,listener));
        return c;
    }


    public QuestionViewHolder setQuest(ViewGroup parent,int text, boolean deafultR, RadioGroup.OnCheckedChangeListener listener){
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = inflater.inflate(R.layout.cuestion_layout, parent, false);
        QuestionViewHolder questionViewHolder = new QuestionViewHolder(layout,listener);
        questionViewHolder.bind(new QuestionViewHolder.Question(text,deafultR),null);
        return questionViewHolder;
    }

    public SpinnerHolder setSpinner(ViewGroup parent, int texthint, StatesSpinnerAdapter adapter){
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = inflater.inflate(R.layout.spinner_view_layout, parent, false);
        SpinnerHolder spinnerHolder = new SpinnerHolder(layout);
        spinnerHolder.bind(new SpinnerHolder.SpinerItem(texthint,adapter),null);
        return spinnerHolder;
    }

    public InputDataViewHolder setInputText(ViewGroup parent,String texthint){
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = inflater.inflate(R.layout.textinput_view_layout, parent, false);
        InputDataViewHolder inputDataViewHolder = new InputDataViewHolder(layout);
        inputDataViewHolder.bind(texthint,null);
        return inputDataViewHolder;
    }


    /*public void addItemOptionMenuIViewHolder(int resource, Object item){


        //GenericHolder holder = new OptionMenuIViewHolder(layout);
        holder.bind(item,this.listenerHolder);
        parent.addView(layout);
        listItems.add(holder);
    }*/
}
