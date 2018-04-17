package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.ui_wallet.builder.ContainerBuilder;
import com.pagatodo.yaganaste.ui_wallet.holders.ButtonsViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.IndicationZoneViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.OptionsViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.StatusZoneViewHolder;
import com.pagatodo.yaganaste.ui_wallet.interfaces.OnItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.utils.ValidatePermissions;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.utils.Constants.PERMISSION_GENERAL;
import static com.pagatodo.yaganaste.utils.Recursos.ID_ESTATUS;

/**
 * Created by ozuniga on 14/02/2017.
 */

public class ElementsWalletAdapter extends RecyclerView.Adapter<OptionsViewHolder> {

    private List<ElementView> elementViews;
    private Activity context;
    private OnItemClickListener listener;

    public ElementsWalletAdapter(Activity context, OnItemClickListener listener) {
        this.context = context;
        this.elementViews = new ArrayList<>();
        this.listener = listener;
    }



    @Override
    public OptionsViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        return ContainerBuilder.getViewHolder(context,parent,elementViews.get(position).getTypeOptions());
    }

    @Override
    public void onBindViewHolder(final OptionsViewHolder holder, final int position) {
        holder.bind(elementViews.get(position), listener);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return this.elementViews.size();
    }

    public void setListOptions(List<ElementView> elementViews){
        this.elementViews = elementViews;
    }


    /*public class DataWallet {
        int columnas;
        int texResource;

        public DataWallet(int columnas, int texResource) {
            this.columnas = columnas;
            this.texResource = texResource;
        }

        public int getColumnas() {
            return columnas;
        }

        public void setColumnas(int columnas) {
            this.columnas = columnas;
        }

        public int getTexResource() {
            return texResource;
        }

        public void setTexResource(int texResource) {
            this.texResource = texResource;
        }
    }*/
}
