package com.pagatodo.yaganaste.ui_wallet.holders;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.MovimientosSbResponse;
import com.pagatodo.yaganaste.ui_wallet.adapters.MovimentsListAdapter;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

public class MovimentHeaderViewHolder extends RecyclerView.ViewHolder {

    private StyleTextView titleSection;
    private RecyclerView rcvItems;
    private MovimentsListAdapter adapter;
    private Context context;
    private LinearLayoutManager mLayoutManager;

    public MovimentHeaderViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.titleSection = itemView.findViewById(R.id.title_secction);
        this.rcvItems = itemView.findViewById(R.id.rcv_items);
        this.adapter = new MovimentsListAdapter();
        this.mLayoutManager = new LinearLayoutManager(this.context);

    }

    public void bind(MovimientosSbResponse itemHead){
        this.titleSection.setText(itemHead.getMes());
        this.rcvItems.setLayoutManager(mLayoutManager);
        this.rcvItems.setAdapter(this.adapter);
        this.adapter.setList(itemHead.getTransacciones());
        this.adapter.notifyDataSetChanged();
    }





}
