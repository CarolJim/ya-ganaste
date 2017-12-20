package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.holders.ButtonsViewHolder;
import com.pagatodo.yaganaste.ui_wallet.interfaces.ElementView;

import java.util.List;

/**
 * Created by icruz on 11/12/2017.
 */

public class ElementsWalletAdpater extends RecyclerView.Adapter<ButtonsViewHolder>{

    private List<ElementView> elementViews;
    private Context context;
    private final OnItemClickListener listener;

    public ElementsWalletAdpater(Context context,List<ElementView> elementViews, OnItemClickListener listener){
        this.context = context;
        this.elementViews = elementViews;
        this.listener = listener;
    }

    @Override
    public ButtonsViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_element, viewGroup, false);
        ButtonsViewHolder pvh = new ButtonsViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ButtonsViewHolder holder, int position) {
            //holder.linearLayout.setOnClickListener(elementViews.get(position).listener(this.context));
            holder.bind(elementViews.get(position),this.listener);
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return this.elementViews.size();
    }

    public interface OnItemClickListener{
        void onItemClick(ElementView elementView);
    }

}
