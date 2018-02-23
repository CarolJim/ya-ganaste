package com.pagatodo.yaganaste.ui_wallet.Builder;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.pojos.InputText;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;
import com.pagatodo.yaganaste.ui_wallet.pojos.TextData;

import java.util.ArrayList;

/**
 * Created by icruz on 09/02/2018.
 */

public class Container {

    private Context context;
    public Container(){

    }

    public Container(Context context){
        this.context = context;
    }
    private ArrayList<OptionMenuItem> options = new ArrayList<>();
    private ArrayList<TextData> textDataList = new ArrayList<>();
    private ArrayList<InputText> inputTextList = new ArrayList<>();
    private ArrayList<InputText.ViewHolderInputText> arrayListInput = new ArrayList<>();

    void addOption(OptionMenuItem options){
        this.options.add(options);
    }

    public void addTextData(TextData textData){
        this.textDataList.add(textData);
    }

    public void addInputText(InputText inputText){
        this.inputTextList.add(inputText);
    }



    ArrayList<OptionMenuItem> getOptions() {
        return this.options;
    }

    ArrayList<TextData> getTextDataList() { return this.textDataList;}

    ArrayList<InputText.ViewHolderInputText> getArrayListInput(){
        return this.arrayListInput;
    }

    public ArrayList<InputText> getInputTextList() {
        return this.inputTextList;
    }

    public void addLayout(ViewGroup parent, InputText inputText) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = inflater.inflate(R.layout.adapter_input_text, parent, false);
        InputText.ViewHolderInputText viewHolderInputText = new InputText.ViewHolderInputText();
        viewHolderInputText.inputLayout = layout.findViewById(R.id.passwordnew);
        viewHolderInputText.editText = layout.findViewById(R.id.editTextpass);
        viewHolderInputText.inputLayout.setHint(inputText.getHintText());
        //viewHolderInputText.inputLayout.setPasswordVisibilityToggleEnabled(true);
        //viewHolderInputText.editText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        parent.addView(layout);
        this.arrayListInput.add(viewHolderInputText);
    }

}
