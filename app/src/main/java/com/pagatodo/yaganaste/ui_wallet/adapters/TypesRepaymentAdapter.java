package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adq.TiposReembolsoResponse;
import com.pagatodo.yaganaste.ui._adapters.OnRecyclerItemClickListener;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.util.List;

/**
 * Created by Omar on 22/02/2018.
 */

public class TypesRepaymentAdapter extends RecyclerView.Adapter<TypesRepaymentAdapter.ViewHolder> {

    List<TiposReembolsoResponse> lstTypes;
    OnRecyclerItemClickListener onRecyclerItemClickListener;
    private int positionSelected;

    public TypesRepaymentAdapter(List<TiposReembolsoResponse> lstTypes,
                                 OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.lstTypes = lstTypes;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
        // Busca el primer elemento seleccionado
        for (int i = 0; i < lstTypes.size(); i++) {
            if (lstTypes.get(i).isConfigurado()) {
                positionSelected = i;
                break;
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_type_repayment, parent, false);
        TypesRepaymentAdapter.ViewHolder vh = new TypesRepaymentAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TiposReembolsoResponse obj = lstTypes.get(position);
        holder.txtDesc.setText(String.format(App.getContext().getString(R.string.row_time_repayment_desc), obj.getComision() + "%"));
        holder.txtTitle.setText(obj.getTipoReembolso());
        if (position == positionSelected) {
            holder.txtTitle.setTextColor(App.getInstance().getResources().getColor(R.color.colorAccent));
            holder.imgType.setImageResource(R.drawable.rdb_pressed);
        } else {
            holder.txtTitle.setTextColor(App.getInstance().getResources().getColor(R.color.edittext));
            holder.imgType.setImageResource(R.drawable.rdb_not_pressed);
        }
    }

    @Override
    public int getItemCount() {
        return lstTypes.size();
    }

    @Override
    public long getItemId(int position) {
        return lstTypes.get(position).getID_TipoReembolso();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout lytRow;
        public StyleTextView txtTitle, txtDesc;
        public ImageView imgType;

        public ViewHolder(View itemView) {
            super(itemView);
            lytRow = (LinearLayout) itemView.findViewById(R.id.lyt_row_type_repayment);
            txtTitle = (StyleTextView) itemView.findViewById(R.id.txt_row_title_type_rep);
            txtDesc = (StyleTextView) itemView.findViewById(R.id.txt_row_desc_type_rep);
            imgType = (ImageView) itemView.findViewById(R.id.img_type_repayment);
            lytRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positionSelected = getAdapterPosition();
                    onRecyclerItemClickListener.onRecyclerItemClick(v, getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }
}
