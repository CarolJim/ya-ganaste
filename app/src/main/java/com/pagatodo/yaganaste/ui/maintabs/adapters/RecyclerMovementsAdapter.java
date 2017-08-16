package com.pagatodo.yaganaste.ui.maintabs.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.ui._adapters.OnRecyclerItemClickListener;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.customviews.MontoTextView;

import java.util.List;

/**
 * @author Juan Guerra
 *         Jordan Rosas 09/08/2017
 */

public class RecyclerMovementsAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private List<ItemMovements<T>> itemMovementses;
    private OnRecyclerItemClickListener listener;

    public RecyclerMovementsAdapter(@NonNull List<ItemMovements<T>> itemMovementses, @Nullable OnRecyclerItemClickListener listener) {
        this.itemMovementses = itemMovementses;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new RecyclerViewHolderMovements(inflater.inflate(R.layout.item_movement, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((RecyclerViewHolderMovements) holder).bindData(itemMovementses.get(position), position, this);
    }


    @Override
    public int getItemCount() {
        return itemMovementses.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onRecyclerItemClick(v, Integer.valueOf(v.getTag().toString()));
        }
    }

    static class RecyclerViewHolderMovements extends RecyclerView.ViewHolder {

        View layoutMovementTypeColor;
        TextView txtItemMovDate;
        TextView txtItemMovMonth;
        TextView txtPremios;
        TextView txtMarca;
        MontoTextView txtMonto;

        private RecyclerViewHolderMovements(View itemView) {
            super(itemView);
            layoutMovementTypeColor = itemView.findViewById(R.id.layout_movement_type_color);
            txtItemMovDate = (TextView) itemView.findViewById(R.id.txt_item_mov_date);
            txtItemMovMonth = (TextView) itemView.findViewById(R.id.txt_item_mov_month);
            txtPremios = (TextView) itemView.findViewById(R.id.txt_premios);
            txtMarca = (TextView) itemView.findViewById(R.id.txt_marca);
            txtMonto = (MontoTextView) itemView.findViewById(R.id.txt_monto);
            //txtItemMovCents = (TextView)itemView.findViewById(R.id.txt_item_mov_cents);
        }

        void bindData(ItemMovements itemMovements, int position, View.OnClickListener clickListener) {
            //String[] monto = Utils.getCurrencyValue(itemMovements.getMonto()).split("\\.");
            layoutMovementTypeColor.setBackgroundResource(itemMovements.getColor());
            txtMonto.setTextColor(ContextCompat.getColor(App.getContext(), itemMovements.getColor()));
            txtPremios.setText(itemMovements.getPremio());
            txtMarca.setText(itemMovements.getMarca());

            txtMonto.setText(StringUtils.getCurrencyValue(Double.toString(itemMovements.getMonto())));//(monto[0].concat("."));

            txtItemMovDate.setText(itemMovements.getDate());
            txtItemMovMonth.setText(itemMovements.getMonth());

            if (itemMovements.getColor() == android.R.color.transparent) {
                txtMonto.setTextColor(ContextCompat.getColor(App.getContext(), R.color.colorAccent));
            }

            itemView.setOnClickListener(clickListener);
            itemView.setTag(position);
        }
    }
}