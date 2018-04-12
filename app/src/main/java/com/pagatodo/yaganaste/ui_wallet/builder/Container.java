package com.pagatodo.yaganaste.ui_wallet.builder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.ui_wallet.holders.GenericHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.holders.PaletteViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.OptionMenuIViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.ViewHolderMenuSegurity;
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
    private OnClickItemHolderListener listenerHolder;
    private ViewGroup parent;
    private View viewItem;

    private ArrayList<GenericHolder> listItems;

    public Container(){

    }

    public Container(Context context, ViewGroup parent, OnClickItemHolderListener listener){
        this.listItems = new ArrayList<>();
        this.context = context;
        this.parent = parent;
        this.listenerHolder = listener;
    }

    public Container(Context context){
        this.context = context;
    }

    public Container(Context context, OptionMenuItem.OnMenuItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public Container(Context context, OnClickItemHolderListener listener){
        this.context = context;
        this.listenerHolder = listener;
    }

    public Container(Context context, ViewGroup parent){
        this.context = context;
        this.parent = parent;
    }

    public void addItemOptionMenuIViewHolder(int resource, Object item){
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = inflater.inflate(resource, this.parent, false);
        GenericHolder holder = new OptionMenuIViewHolder(layout);
        holder.bind(item,this.listenerHolder);
        parent.addView(layout);
        listItems.add(holder);
    }

    public void addItemViewHolderMenuSegurity(Object item){
        LayoutInflater inflater = LayoutInflater.from(this.context);
        OptionMenuItem optionMenuItem = (OptionMenuItem) item;
        View layout = null;
        if (optionMenuItem.getIndication() != null && optionMenuItem.getIndication() == RAW) {
            layout = inflater.inflate(R.layout.menu_navegation_drawwer_adpater, parent, false);
        }
        if (optionMenuItem.getIndication() != null && optionMenuItem.getIndication() == RADIOBUTTON) {
            layout = inflater.inflate(R.layout.option_menu_tem_view, parent, false);
        }

        GenericHolder holder = new ViewHolderMenuSegurity(layout);
        holder.bind(item,this.listenerHolder);
        parent.addView(layout);
        listItems.add(holder);
    }

    public ViewGroup getParent(){
        return this.parent;
    }

    public Context getContext(){
        return this.context;
    }

    public GenericHolder getItem(int position){
        return listItems.get(position);
    }

    private ArrayList<OptionMenuItem> options = new ArrayList<>();
    private ArrayList<TextData> textDataList = new ArrayList<>();
    private ArrayList<InputText> inputTextList = new ArrayList<>();
    private ArrayList<InputText.ViewHolderInputText> arrayListInput = new ArrayList<>();
    private ArrayList<OptionMenuIViewHolder> arrayListOptionMenu = new ArrayList<>();
    private ArrayList<OptionMenuItem.ViewHolderMenuSegurity> arrayListOptionMenuSegurity = new ArrayList<>();
    private ArrayList<PaletteViewHolder> holdersList = new ArrayList<>();

    void addOption(OptionMenuItem options){
        this.options.add(options);
    }

    public void addTextDataA(TextData textData){
        this.textDataList.add(textData);
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

    /*public void addOptionMenu(ViewGroup parent, final OptionMenuItem optionMenuItem) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.option_menu_tem_view, parent, false);
        OptionMenuIViewHolder viewHolder = new OptionMenuIViewHolder(view);
        viewHolder.bind(optionMenuItem,listener);
        parent.addView(view);
        this.arrayListOptionMenu.add(viewHolder);
    }*/

    ArrayList<OptionMenuItem> getOptions() {
        return this.options;
    }
    ArrayList<TextData> getTextDataList() { return this.textDataList;}
    ArrayList<InputText.ViewHolderInputText> getArrayListInput(){
        return this.arrayListInput;
    }
    ArrayList<OptionMenuIViewHolder> getArrayListOptionMenu(){ return this.arrayListOptionMenu;}
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

    public void addHolder(Favoritos favorito, PaletteViewHolder.OnClickListener listener){
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = inflater.inflate(R.layout.row_envios, parent, false);
        PaletteViewHolder holder =  new PaletteViewHolder(layout);
        holder.bind(favorito,listener);
        this.parent.addView(layout);
        this.holdersList.add(holder);
    }

    public void addSimpleHolder(final Favoritos favorito, final PaletteViewHolder.OnClickListener listener){
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View layout = inflater.inflate(R.layout.row_envios, parent, false);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(favorito);
            }
        });
        this.parent.addView(layout);
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
                    listenerHolder.onClick(optionMenuItem);
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

    public ArrayList<PaletteViewHolder>  getHoldersList(){
        return this.holdersList;
    }
}
