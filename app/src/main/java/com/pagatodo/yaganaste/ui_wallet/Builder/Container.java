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

import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.INDICATION.RADIOBUTTON;
import static com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem.INDICATION.RAW;

/**
 * Created by icruz on 09/02/2018.
 */

public class Container {

    private Context context;
    private OptionMenuItem.OnMenuItemClickListener listener;
    private ViewGroup parent;

    public Container(){

    }

    public Container(ViewGroup parent){
        this.parent = parent;
    }

    public Container(Context context){
        this.context = context;
    }

    public Container(Context context, OptionMenuItem.OnMenuItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }
    private ArrayList<OptionMenuItem> options = new ArrayList<>();
    private ArrayList<TextData> textDataList = new ArrayList<>();
    private ArrayList<InputText> inputTextList = new ArrayList<>();
    private ArrayList<InputText.ViewHolderInputText> arrayListInput = new ArrayList<>();
    private ArrayList<OptionMenuItem.ViewHolderOptionMenuItme> arrayListOptionMenu = new ArrayList<>();
    private ArrayList<OptionMenuItem.ViewHolderMenuSegurity> arrayListOptionMenuSegurity = new ArrayList<>();

    void addOption(OptionMenuItem options){
        this.options.add(options);
    }

    public void addTextData(TextData textData){
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = inflater.inflate(R.layout.item_detallet_mov, this.parent, false);
        TextData.ViewHolderTextData viewHolder = new TextData.ViewHolderTextData();
        viewHolder.leftText = layout.findViewById(R.id.txt_left);
        viewHolder.rightText = layout.findViewById(R.id.txt_right);
        this.parent.addView(layout);
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
    ArrayList<OptionMenuItem.ViewHolderOptionMenuItme> getArrayListOptionMenu(){ return this.arrayListOptionMenu;}
    public ArrayList<OptionMenuItem.ViewHolderMenuSegurity> getArrayListOptionMenuSegurity(){ return this.arrayListOptionMenuSegurity;}

    public ArrayList<InputText> getInputTextList() {
        return this.inputTextList;
    }

    public void addLayout(ViewGroup parent, InputText inputText) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = inflater.inflate(R.layout.adapter_input_text, parent, false);
        InputText.ViewHolderInputText viewHolderInputText = new InputText.ViewHolderInputText();
        viewHolderInputText.inputLayout = layout.findViewById(R.id.passwordnew);
        viewHolderInputText.editText = layout.findViewById(R.id.editTextpass);
        viewHolderInputText.inputLayout.setHint(context.getResources().getString(inputText.getHintText()));
        //viewHolderInputText.inputLayout.setPasswordVisibilityToggleEnabled(true);
        //viewHolderInputText.editText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        parent.addView(layout);
        this.arrayListInput.add(viewHolderInputText);
    }

    public InputText.ViewHolderInputText addLayoutPass(ViewGroup parent, InputText inputText) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = inflater.inflate(R.layout.adapter_input_text, parent, false);
        InputText.ViewHolderInputText viewHolderInputText = new InputText.ViewHolderInputText();
        viewHolderInputText.inputLayout = layout.findViewById(R.id.passwordnew);
        viewHolderInputText.editText = layout.findViewById(R.id.editTextpass);
        viewHolderInputText.inputLayout.setHint(context.getResources().getString(inputText.getHintText()));
        //viewHolderInputText.inputLayout.setPasswordVisibilityToggleEnabled(true);
        //viewHolderInputText.editText.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);

        parent.addView(layout);
        this.arrayListInput.add(viewHolderInputText);
        return viewHolderInputText;
    }

    public void addOptionMenu(ViewGroup parent, final OptionMenuItem optionMenuItem) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = inflater.inflate(R.layout.option_menu_tem_view, parent, false);
        OptionMenuItem.ViewHolderOptionMenuItme viewHolder = new OptionMenuItem.ViewHolderOptionMenuItme();
        viewHolder.relativeLayout = layout.findViewById(R.id.item_menu);
        viewHolder.imageView = layout.findViewById(R.id.ic_item);
        viewHolder.title = layout.findViewById(R.id.title);
        viewHolder.dividerList = layout.findViewById(R.id.dividerList);
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnMenuItem(optionMenuItem);
            }
        });
        viewHolder.imageView.setBackgroundResource(optionMenuItem.getResourceItem());
        viewHolder.title.setText(optionMenuItem.getResourceTitle());

        if (optionMenuItem.isDivider()) {
            viewHolder.dividerList.setVisibility(View.VISIBLE);
        } else {
            viewHolder.dividerList.setVisibility(View.INVISIBLE);
        }
        parent.addView(layout);
        this.arrayListOptionMenu.add(viewHolder);
    }

    public void addOptionMenuSegurity(ViewGroup parent, final OptionMenuItem optionMenuItem) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = null;
        OptionMenuItem.ViewHolderMenuSegurity viewHolder = new OptionMenuItem.ViewHolderMenuSegurity();
        if (optionMenuItem.getIndication() != null && optionMenuItem.getIndication() == RAW) {
            layout = inflater.inflate(R.layout.menu_navegation_drawwer_adpater, parent, false);
            viewHolder.title = layout.findViewById(R.id.title);
            viewHolder.raw = layout.findViewById(R.id.raw_item);
            viewHolder.relativeLayout = layout.findViewById(R.id.item_menu);
            viewHolder.title.setText(optionMenuItem.getResourceTitle());
            viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnMenuItem(optionMenuItem);
                }
            });
            parent.addView(layout);
        }
        if (optionMenuItem.getIndication() != null && optionMenuItem.getIndication() == RADIOBUTTON) {
            layout = inflater.inflate(R.layout.menu_itme_view_subtitle, parent, false);
            viewHolder.title = layout.findViewById(R.id.title);
            viewHolder.subtitle = layout.findViewById(R.id.subtitle);
            viewHolder.raw = layout.findViewById(R.id.raw_item);
            viewHolder.radioGroup = layout.findViewById(R.id.radio_group);
            viewHolder.radioButtonNo = layout.findViewById(R.id.radiobutton_no);
            viewHolder.radioButtonSi = layout.findViewById(R.id.radiobutton_si);
            viewHolder.relativeLayout = layout.findViewById(R.id.item_menu);
            viewHolder.subtitle.setVisibility(View.VISIBLE);
            viewHolder.title.setText(optionMenuItem.getResourceTitle());
            viewHolder.subtitle.setText(optionMenuItem.getSubtitle());
            viewHolder.raw.setVisibility(View.GONE);
            viewHolder.radioGroup.setVisibility(View.VISIBLE);
            parent.addView(layout);
        }

        this.arrayListOptionMenuSegurity.add(viewHolder);
    }


}
