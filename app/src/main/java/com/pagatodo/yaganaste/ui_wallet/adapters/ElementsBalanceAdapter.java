package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.holders.ButtonsViewHolder;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;

import java.util.List;

import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_CUENTA_BLOQUEADA;
import static com.pagatodo.yaganaste.utils.Recursos.ESTATUS_CUENTA_DESBLOQUEADA;

/**
 * Created by ozuniga on 14/02/2017.
 */

public class ElementsBalanceAdapter extends RecyclerView.Adapter<ButtonsViewHolder> {

    private List<ElementView> elementViews;
    private Context context;
    private OnItemClickListener listener;

    public ElementsBalanceAdapter(Context context, OnItemClickListener listener, List<ElementView> elementViews) {
        this.context = context;
        this.elementViews = elementViews;
        this.listener = listener;
    }

    @Override
    public ButtonsViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ButtonsViewHolder(this.context,inflater.inflate(R.layout.balance_element, parent, false));
    }

    @Override
    public void onBindViewHolder(final ButtonsViewHolder holder, final int position) {
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

    public interface OnItemClickListener {
        void onItemClick(ElementView elementView);
    }
}
