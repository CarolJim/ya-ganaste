package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosSbResponse;
import com.pagatodo.yaganaste.ui_wallet.holders.MovimentHeaderViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MovementsHeadAdpater extends RecyclerView.Adapter<MovimentHeaderViewHolder> {

    private List<MovimientosSbResponse> listM;
    private Context context;

    public MovementsHeadAdpater(Context context){
        this.context = context;
        this.listM = new ArrayList<>();
    }

    public void setList(List<MovimientosSbResponse> listM){
        this.listM = listM;
    }


    @Override
    public MovimentHeaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MovimentHeaderViewHolder(this.context,inflater.inflate(R.layout.movement_title_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MovimentHeaderViewHolder holder, int position) {
        holder.bind(listM.get(position));
    }

    @Override
    public int getItemCount() {
        return listM.size();
    }
}
