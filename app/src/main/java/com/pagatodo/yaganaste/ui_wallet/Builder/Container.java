package com.pagatodo.yaganaste.ui_wallet.Builder;

import com.pagatodo.yaganaste.ui_wallet.pojos.InputText;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;
import com.pagatodo.yaganaste.ui_wallet.pojos.TextData;

import java.util.ArrayList;

/**
 * Created by icruz on 09/02/2018.
 */

public class Container {

    private ArrayList<OptionMenuItem> options = new ArrayList<>();
    private ArrayList<TextData> textDataList = new ArrayList<>();
    private ArrayList<InputText> inputTextList = new ArrayList<>();

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

    public ArrayList<InputText> getInputTextList() {
        return this.inputTextList;
    }



}
