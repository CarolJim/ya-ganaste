package com.pagatodo.yaganaste.ui.maintabs.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.dto.ItemMovements;
import com.pagatodo.yaganaste.ui._adapters.OnRecyclerItemClickListener;
import com.pagatodo.yaganaste.utils.Utils;

import java.util.List;

/**
 * @author Juan Guerra
 */

public class RecyclerMovementsAdapter<T> extends RecyclerView.Adapter<RecyclerMovementsAdapter.RecyclerViewHolderMovements> implements View.OnClickListener {

    private List<ItemMovements<T>> itemMovementses;
    private Context context;
    private OnRecyclerItemClickListener listener;

    public RecyclerMovementsAdapter(@NonNull Context context, @NonNull List<ItemMovements<T>> itemMovementses, @Nullable OnRecyclerItemClickListener listener) {
        this.itemMovementses = itemMovementses;
        this.context = context;
        this.listener = listener;
    }

    public RecyclerMovementsAdapter(@NonNull Context context, @NonNull List<ItemMovements<T>> itemMovementses) {
        this(context, itemMovementses, null);
    }

    @Override
    public RecyclerViewHolderMovements onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movement, parent,false);
        return new RecyclerViewHolderMovements(layoutView);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolderMovements holder, int position) {
        ItemMovements itemMovements = itemMovementses.get(position);
        String[] monto = Utils.getCurrencyValue(itemMovements.getMonto()).split("\\.");

        //holder.layoutMovementTypeColor.setBackgroundResource(itemMovements.getColor());
        //holder.txtMonto.setTextColor(ContextCompat.getColor(context, itemMovements.getColor()));
        holder.layoutMovementTypeColor.setBackgroundResource(R.color.greencolor);
        holder.txtMonto.setTextColor(ContextCompat.getColor(context, R.color.greencolor));
        holder.txtPremios.setText(itemMovements.getPremio());
        holder.txtMarca.setText(itemMovements.getMarca());

        holder.txtMonto.setText(monto[0]);

        holder.txtItemMovDate.setText(itemMovements.getDate());
        holder.txtItemMovMonth.setText(itemMovements.getMonth());

        if (Utils.getDoubleValue(monto[1]) > 0) {
            holder.txtItemMovCents.setText(monto[1]);
            holder.txtItemMovCents.setTextColor(ContextCompat.getColor(context, itemMovements.getColor()));
        } else {
            holder.txtItemMovCents.setVisibility(View.GONE);
        }

        if (itemMovementses.get(position).getColor() == android.R.color.transparent) {
            holder.txtMonto.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }

        holder.itemView.setOnClickListener(this);
        holder.itemView.setTag(position);
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

    public static class RecyclerViewHolderMovements extends RecyclerView.ViewHolder {

        View layoutMovementTypeColor;
        TextView txtItemMovDate;
        TextView txtItemMovMonth;
        TextView txtPremios;
        TextView txtMarca;
        TextView txtMonto;
        TextView txtItemMovCents;

        private RecyclerViewHolderMovements(View itemView) {
            super(itemView);
            layoutMovementTypeColor = itemView.findViewById(R.id.layout_movement_type_color);
            txtItemMovDate = (TextView)itemView.findViewById(R.id.txt_item_mov_date);
            txtItemMovMonth = (TextView)itemView.findViewById(R.id.txt_item_mov_month);
            txtPremios = (TextView)itemView.findViewById(R.id.txt_premios);
            txtMarca = (TextView)itemView.findViewById(R.id.txt_marca);
            txtMonto = (TextView)itemView.findViewById(R.id.txt_monto);
            txtItemMovCents = (TextView)itemView.findViewById(R.id.txt_item_mov_cents);
        }
    }

}