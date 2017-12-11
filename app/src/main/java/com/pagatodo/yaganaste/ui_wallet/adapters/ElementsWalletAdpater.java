package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import com.pagatodo.yaganaste.ui_wallet.holders.ButtonsViewHolder;

import java.util.List;

/**
 * Created by icruz on 11/12/2017.
 */

public class ElementsWalletAdpater extends RecyclerView.Adapter<ButtonsViewHolder>{

    private List<Button> buttonList;

    public ElementsWalletAdpater(List<Button> buttonList){
        this.buttonList = buttonList;
    }

    @Override
    public ButtonsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());


        ButtonsViewHolder buttonsViewHolder = new ButtonsViewHolder();
        return null;
    }

    @Override
    public void onBindViewHolder(ButtonsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
