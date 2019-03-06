package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.ui_wallet.holders.GenericHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.patterns.builders.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;

import java.util.ArrayList;
import java.util.List;

public class ElementsWalletAdapter extends RecyclerView.Adapter<GenericHolder> {

    private List<ElementView> elementViews;
    private Activity context;
    private OnClickItemHolderListener listener;
    private boolean isBalance;

    public ElementsWalletAdapter(Activity context, OnClickItemHolderListener listener, boolean isBalance) {
        this.context = context;
        this.elementViews = new ArrayList<>();
        this.listener = listener;
        this.isBalance = isBalance;
    }


    @NonNull
    @Override
    public GenericHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        if (!isBalance) {
            return ContainerBuilder.getViewHolder(context, parent, elementViews.get(position).getTypeOptions());
        } else {
            return ContainerBuilder.getViewHolderBalance(context, parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final GenericHolder holder, final int position) {
        holder.bind(elementViews.get(position), listener);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
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
