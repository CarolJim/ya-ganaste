package com.pagatodo.yaganaste.ui_wallet.patterns.builders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui.account.register.adapters.BussinesLineSpinnerAdapter;
import com.pagatodo.yaganaste.ui.account.register.adapters.EnumSpinnerAdapter;
import com.pagatodo.yaganaste.ui.account.register.adapters.StatesSpinnerAdapter;
import com.pagatodo.yaganaste.ui_wallet.holders.InputDataViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.LinearTextViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
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

    public LinearTextViewHolder setLineraText(ViewGroup parent,
                                              LinearTextViewHolder.Videotutorial item,
                                              OnClickItemHolderListener listener){
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = inflater.inflate(R.layout.linear_text_layout, parent, false);
        LinearTextViewHolder linearTextViewHolder = new LinearTextViewHolder(layout);
        linearTextViewHolder.bind(item,listener);
        return linearTextViewHolder;
    }
}
