package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.DataSourceResult;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IPaymentFragment;
import com.pagatodo.yaganaste.ui_wallet.views.DataFavoritosGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FranciscoManzo on 31/01/2018.
 */

class AdapterPagosRV extends RecyclerView.Adapter<AdapterPagosRV.ViewHolder>
implements IPaymentFragment {
    ArrayList<ArrayList<DataFavoritos>> mFullListaFav;
    ArrayList<DataFavoritos> mAuxFav, mAuxFav2;
    private ArrayList mRecargarGrid;

    public AdapterPagosRV(ArrayList<ArrayList<DataFavoritos>> mFullListaFav) {
        this.mFullListaFav = mFullListaFav;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public GridView gvHolder;
        public ViewHolder(View itemView) {
            super(itemView);
            gvHolder = (GridView) itemView.findViewById(R.id.gvHolder);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_pagos_rv_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.myText.setText(mDataset[position]);
        mAuxFav = new ArrayList<>();
        mRecargarGrid = new ArrayList<>();
        mRecargarGrid = getDataList(position);
        holder.gvHolder.setAdapter(new PaymentAdapterRV(mRecargarGrid, this,
                1, 1));
    }

    private ArrayList getDataList(int position) {
        mAuxFav = mFullListaFav.get(position);

        for (int x = 0; x < mAuxFav.size(); x++) {
            mRecargarGrid.add(new DataFavoritosGridView(
                    mAuxFav.get(x).getColorMarca(),
                    mAuxFav.get(x).getNombre(),
                    mAuxFav.get(x).getImagenURL()));
        }

        return mRecargarGrid;
    }

    @Override
    public int getItemCount() {
        return mFullListaFav.size();
    }

    @Override
    public void sendData(int position, int mType) {

    }

    @Override
    public void editFavorite(int position, int mType) {

    }

    @Override
    public void setDataFavorite(List<DataFavoritos> catalogos, int typeDataFav) {

    }

    @Override
    public void sendFavoriteToView(DataFavoritos dataFavoritos, int mType) {

    }

    @Override
    public void errorFail(DataSourceResult error) {

    }

    @Override
    public void errorService() {

    }
}
