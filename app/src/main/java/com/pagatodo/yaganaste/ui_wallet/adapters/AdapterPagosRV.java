package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.ui_wallet.interfaces.IPaymentFragment;
import com.pagatodo.yaganaste.ui_wallet.views.DataFavoritosGridView;

import java.util.ArrayList;

/**
 * Created by FranciscoManzo on 31/01/2018.
 * Genera el RecyclarView y en cada posicion, usa la lista general, para hacer una posicion 0,1,N
 * con sublistas de 8 elementos o menos
 */

class AdapterPagosRV extends RecyclerView.Adapter<AdapterPagosRV.ViewHolder> {
    ArrayList<ArrayList<Favoritos>> mFullListaFav;
    ArrayList<Favoritos> mAuxFav, mAuxFav2;
    private ArrayList mRecargarGrid;
    IPaymentFragment mView;
    int mType, typeOperation;

    public AdapterPagosRV(ArrayList<ArrayList<Favoritos>> mFullListaFav, IPaymentFragment mView,
                          int mType, int typeOperation) {
        this.mFullListaFav = mFullListaFav;
        this.mView = mView;
        this.mType = mType;
        this.typeOperation = typeOperation;
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

    /**
     * Generamos el GridView en cada posicion del RV, es importante notar que pasamos la vista que
     * de la interfase que respondera, y la position del RV.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.myText.setText(mDataset[position]);
        mAuxFav = new ArrayList<>();
        mRecargarGrid = new ArrayList<>();
        mRecargarGrid = getDataList(position);
        holder.gvHolder.setAdapter(new PaymentAdapterGV(mRecargarGrid, mView,
                mType, typeOperation, position));
    }

    /**
     * Genera una lista particular para el View del GridView, con elementos sencillos, solo color de marca
     * nombre y url de la imagen, para no trabajar con todas las propiedades de DataFavorites
     * @param position
     * @return
     */
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
}
