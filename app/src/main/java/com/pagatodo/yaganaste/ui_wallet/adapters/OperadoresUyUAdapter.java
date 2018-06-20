package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;

import com.pagatodo.yaganaste.data.room_db.entities.Rewards;
import com.pagatodo.yaganaste.ui._adapters.OnRecyclerItemClickListener;
import com.pagatodo.yaganaste.ui.maintabs.fragments.PersonalAccountFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.DetalleOperadorFragment;
import com.pagatodo.yaganaste.ui_wallet.fragments.OperadoresUYUFragment;
import com.pagatodo.yaganaste.data.room_db.entities.Operadores;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.ui._controllers.manager.SupportFragmentActivity.EVENT_SESSION_EXPIRED;

public class OperadoresUyUAdapter extends RecyclerView.Adapter<OperadoresUyUAdapter.ViewHolder> {

    List<Operadores> operadoresService, operadores;
    Activity activity;
    OnRecyclerItemClickListener listener;


    public OperadoresUyUAdapter(List<Operadores> operadores, Activity activity ,OnRecyclerItemClickListener listener) {
        this.operadoresService = operadores;
        this.activity= activity;
        this.listener= listener;
        this.operadores = new ArrayList<>();
        for (Operadores op : operadoresService) {
            if (!op.getIsAdmin()) {
                this.operadores.add(op);
            }
        }
    }

    @Override
    public OperadoresUyUAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_operadores, parent, false);
        return new OperadoresUyUAdapter.ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(OperadoresUyUAdapter.ViewHolder holder, int position) {
        if (operadores.size() > 0) {
     //       OperadoresResponse operador = operadores.get(position);
       //     holder.txtTitle.setText(operador.getNombreUsuario());
            if (position % 2 != 0) {
                holder.row.setBackgroundColor(Color.WHITE);
            } else {
                holder.row.setBackgroundColor(App.getContext().getResources().getColor(R.color.backgraund_wallet));
            }
         //   if (operador.getIdEstatusUsuario()==1){
           //     holder.txtTitle.setTextColor(Color.parseColor("#00A1E1"));
           // }else {
           //     holder.txtTitle.setTextColor(Color.parseColor("#D0021B"));
           // }

            holder.txtTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onRecyclerItemClick(view,position);

                }
            });


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
