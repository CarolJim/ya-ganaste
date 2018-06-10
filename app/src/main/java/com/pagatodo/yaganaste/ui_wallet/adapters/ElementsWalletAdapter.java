package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.ui_wallet.patterns.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.holders.OptionsViewHolder;
import com.pagatodo.yaganaste.ui_wallet.interfaces.OnItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ozuniga on 14/02/2017.
 */

public class ElementsWalletAdapter extends RecyclerView.Adapter<OptionsViewHolder> {

    private List<ElementView> elementViews;
    private Activity context;
    private OnItemClickListener listener;
    private boolean isBalance = false;

    public ElementsWalletAdapter(Activity context, OnItemClickListener listener, boolean isBalance) {
        this.context = context;
        this.elementViews = new ArrayList<>();
        this.listener = listener;
        this.isBalance = isBalance;
    }

    @Override
    public OptionsViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        if (!isBalance) {
            return ContainerBuilder.getViewHolder(context, parent, elementViews.get(position).getTypeOptions());
        } else {
            return ContainerBuilder.getViewHolderBalance(context, parent);
        }
    }

    @Override
    public void onBindViewHolder(final OptionsViewHolder holder, final int position) {
        holder.bind(elementViews.get(position), listener);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return this.elementViews.size();
    }

    public void setListOptions(List<ElementView> elementViews) {
        this.elementViews = elementViews;
    }

}
