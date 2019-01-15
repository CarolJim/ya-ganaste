package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.provider.ContactsContract;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui._adapters.OnRecyclerItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementPromocion;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.ArrayList;

public class AdapterPromociones extends  RecyclerView.Adapter<AdapterPromociones.ViewHolder> {

    private ArrayList<ElementPromocion> promocions;

    OnRecyclerItemClickListener listener;

    public AdapterPromociones(ArrayList<ElementPromocion> promocions, OnRecyclerItemClickListener listener) {
        this.promocions = promocions;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_promociones, viewGroup, false);
        return new AdapterPromociones.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ElementPromocion promocion = promocions.get(position);
        holder.titulo.setText(promocion.getTitlo());
        holder.vigencia.setText(promocion.getVigencia());
        if (!promocion.isCasa()){
            holder.casa.setVisibility(View.INVISIBLE);
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRecyclerItemClick(view,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return promocions.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        StyleTextView titulo,vigencia;
        LinearLayout item;
        ImageView casa;

        public ViewHolder(View itemView) {
            super(itemView);
            titulo= (StyleTextView) itemView.findViewById(R.id.titulo_promo);
            vigencia= (StyleTextView) itemView.findViewById(R.id.sub_vigencia);
            casa= (ImageView) itemView.findViewById(R.id.img_casa);
            item = (LinearLayout) itemView.findViewById(R.id.row_promo);
        }
    }
}
