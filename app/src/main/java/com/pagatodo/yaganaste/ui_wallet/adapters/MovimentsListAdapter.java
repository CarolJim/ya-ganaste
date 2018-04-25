package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.TransaccionesSbResponse;
import com.pagatodo.yaganaste.ui_wallet.holders.MovementsViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MovimentsListAdapter extends RecyclerView.Adapter<MovementsViewHolder> {

    private List<TransaccionesSbResponse> listTrasaction;

    public MovimentsListAdapter(){
        this.listTrasaction = new ArrayList<>();
    }

    public void setList(List<TransaccionesSbResponse> items){
        this.listTrasaction = items;
    }

    public void setItem(TransaccionesSbResponse item){
        this.listTrasaction.add(item);
    }

    public TransaccionesSbResponse getItem(int position){
        return this.listTrasaction.get(position);
    }

    @Override
    public MovementsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MovementsViewHolder(inflater.inflate(R.layout.movement_sb_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MovementsViewHolder holder, int position) {
        boolean ispar = false;
        if (position %2 == 0) {
            ispar = true;
        }
        holder.bind(this.listTrasaction.get(position),ispar);
    }

    @Override
    public int getItemCount() {
        return listTrasaction.size();
    }
}
