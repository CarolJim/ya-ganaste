package com.pagatodo.yaganaste.ui_wallet.patterns;

import android.content.Context;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.patterns.components.BusinessDataComposite;
import com.pagatodo.yaganaste.ui_wallet.patterns.components.ButtonLeaf;
import com.pagatodo.yaganaste.ui_wallet.patterns.components.InputTextLeaf;
import com.pagatodo.yaganaste.ui_wallet.patterns.components.SpinnerLeaf;
import com.pagatodo.yaganaste.ui_wallet.patterns.components.TextLeaf;
import com.pagatodo.yaganaste.ui_wallet.patterns.interfaces.Component;
import com.pagatodo.yaganaste.utils.customviews.StyleButton;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

public class FormBuilder {

    private Context context;

    public FormBuilder(Context context) {
        this.context = context;
    }

    public TextLeaf setText(int resText){
        return new TextLeaf(new StyleTextView(context), resText);
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


}
