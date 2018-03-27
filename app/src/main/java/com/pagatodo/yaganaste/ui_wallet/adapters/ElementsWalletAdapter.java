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

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.SingletonUser;
import com.pagatodo.yaganaste.ui_wallet.holders.ButtonsViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.IndicationZoneViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.OptionsViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.StatusZoneViewHolder;
import com.pagatodo.yaganaste.ui_wallet.interfaces.OnItemClickListener;
import com.pagatodo.yaganaste.ui_wallet.pojos.ElementView;
import com.pagatodo.yaganaste.utils.ValidatePermissions;

import java.util.List;

import static com.pagatodo.yaganaste.utils.Constants.PERMISSION_GENERAL;

/**
 * Created by ozuniga on 14/02/2017.
 */

public class ElementsWalletAdapter extends RecyclerView.Adapter<OptionsViewHolder> {

    private List<ElementView> elementViews;

    private Activity context;
    private OnItemClickListener listener;
    private int state;
    OptionsViewHolder op;

    public ElementsWalletAdapter(Activity context, OnItemClickListener listener, List<ElementView> elementViews, int state) {
        this.context = context;
        this.elementViews = elementViews;
        this.listener = listener;
        this.state = state;
        this.op = null;
    }

    @Override
    public OptionsViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        op = null;

        switch (this.state){
            case 0:
                op = new ButtonsViewHolder(this.context,inflater.inflate(R.layout.view_element, parent, false));
                break;
            case 1:
                op = new IndicationZoneViewHolder(this.context,inflater.inflate(R.layout.indicator_zone, parent, false));
                break;
            case 2:
                op = new StatusZoneViewHolder(this.context,inflater.inflate(R.layout.indicator_zone_tipo_uno, parent, false));

                int Idestatus;
                Idestatus = SingletonUser.getInstance().getDataUser().getIdEstatus();
/*
                if (SingletonUser.getInstance().getDataUser().isEsAgente()
                        && Idestatus == IdEstatus.I7.getId()) {
                    //loadFragment(StatusRegisterAdquirienteFragment.newInstance(), Direction.FORDWARD); 08 - Proceso Revisando
                    op = new StatusZoneViewHolder(this.context,inflater.inflate(R.layout.indicator_zone_tipo_uno, parent, false));
                }else if (SingletonUser.getInstance().getDataUser().isEsAgente()
                        && Idestatus == IdEstatus.I8.getId()) {
                    //loadFragment(StatusRegisterAdquirienteFragment.newInstance(), Direction.FORDWARD); 08 - Proceso Revisando
                    op = new StatusZoneViewHolder(this.context,inflater.inflate(R.layout.indicator_zone_tipo_uno, parent, false));
                } else if (SingletonUser.getInstance().getDataUser().isEsAgente() &&
                        Idestatus == IdEstatus.I9.getId()) {
                    //loadFragment(StatusRegisterAdquirienteFragment.newInstance(), Direction.FORDWARD); 11 - Proceso Error Docs
                    op = new StatusZoneViewHolder(this.context,inflater.inflate(R.layout.indicator_zone_tipo_uno, parent, false));

                }else if (SingletonUser.getInstance().getDataUser().isEsAgente() &&
                        Idestatus == IdEstatus.I10.getId()) {
                    //loadFragment(StatusRegisterAdquirienteFragment.newInstance(), Direction.FORDWARD); 13 - Proceso Rechazado

                }else if (SingletonUser.getInstance().getDataUser().isEsAgente() &&
                        Idestatus == IdEstatus.I11.getId()) {
                    //loadFragment(StatusRegisterAdquirienteFragment.newInstance(), Direction.FORDWARD); 09 - Proceso Aprobado

                }else if (SingletonUser.getInstance().getDataUser().isEsAgente() &&
                        Idestatus == IdEstatus.I13.getId()) {
                    //loadFragment(StatusRegisterAdquirienteFragment.newInstance(), Direction.FORDWARD); 13 - Proceso Rechazado
                }else if  (App.getInstance().getPrefs().containsData(ADQ_PROCESS)) {
                    //loadFragment(DocumentosFragment.newInstance(), Direction.FORDWARD);
                    //showBack(true);
                } else {
                    //loadFragment(DatosNegocioFragment.newInstance(girosComercio), Direction.FORDWARD, true);
                    //loadFragment(InformacionAdicionalFragment.newInstance(), Direction.FORDWARD, true);
                }*/
                break;

        }

        return op;
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

    public class DataWallet {
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
    }
}
