package com.pagatodo.yaganaste.ui_wallet.patterns;

import android.content.Context;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
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

    public FormBuilder(Context context) {
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

    public InputTextLeaf setInputTextLeaf() {
        return new InputTextLeaf(this.context);
    }

    public ButtonLeaf setButtonLeaf(int resText){
        return new ButtonLeaf(new StyleButton(context),resText);
    }

    public CuestionLeaf setQuestion(int resText, boolean check, RadioGroup.OnCheckedChangeListener listener) { return new CuestionLeaf(this.context,resText,check, listener);}

    public Component formBusinessData(){
        Component c = new BusinessDataComposite(new StyleTextView(this.context),R.string.bussinesInfoTitle);
        //c.add(setText(R.string.bussinesInfoTitle));
        c.add(setInputTextLeaf());
        c.add(setSpinner());
        c.add(setSpinner());
        c.add(setInputTextLeaf());
        c.add(setInputTextLeaf());
        c.add(setButtonLeaf(R.string.nextButton));
        return c;
    }

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


}
