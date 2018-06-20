package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Operadores;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.List;

public class OperadoresUyUAdapter extends RecyclerView.Adapter<OperadoresUyUAdapter.ViewHolder> {

    List<Operadores> operadoresService, operadores;

    public OperadoresUyUAdapter(List<Operadores> operadores) {
        this.operadoresService = operadores;
        this.operadores = new ArrayList<>();
        for (Operadores op : operadoresService) {
            if (!op.getIsAdmin()) {
                this.operadores.add(op);
            }
        }
    }

    @Override
    public OperadoresUyUAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rewards_starbucks, parent, false);
        return new OperadoresUyUAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OperadoresUyUAdapter.ViewHolder holder, int position) {
        if (operadores.size() > 0) {
            Operadores reward = operadores.get(position);
            holder.txtTitle.setText(reward.getNombreUsuario());
            if (position % 2 != 0) {
                holder.row.setBackgroundColor(Color.WHITE);
            } else {
                holder.row.setBackgroundColor(App.getContext().getResources().getColor(R.color.backgraund_wallet));
            }
        } else {
            holder.txtTitle.setText(App.getContext().getString(R.string.sin_operadores));
        }
    }

    @Override
    public int getItemCount() {
        return operadores.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout row;
        StyleTextView txtTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            row = (LinearLayout) itemView.findViewById(R.id.row_rewards);
            txtTitle = (StyleTextView) itemView.findViewById(R.id.txt_title_rewards);
        }
    }
}
