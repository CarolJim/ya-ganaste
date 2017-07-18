package com.pagatodo.yaganaste.ui.maintabs.adapters;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataLocalizaSucursal;
import com.pagatodo.yaganaste.ui.maintabs.fragments.deposits.DepositsMapFragment;
import com.pagatodo.yaganaste.ui.maintabs.fragments.deposits.MyInterfase;
import com.pagatodo.yaganaste.utils.UtilsLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Jordan on 23/05/2017.
 */

public class RecyclerSucursalesAdapter extends RecyclerView.Adapter<RecyclerSucursalesAdapter.MyViewHolder> {

    public ArrayList<DataLocalizaSucursal> arraylist;
    private List<DataLocalizaSucursal> sucursalList;
    private Location myLocation;
    private Context mContext;
    MyInterfase myInterfase;

    public RecyclerSucursalesAdapter(List<DataLocalizaSucursal> l, Location location, MyInterfase myInterfase) {
        this.sucursalList = l;
        this.myLocation = location;
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(l);
        this.myInterfase = myInterfase;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sucursal_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DataLocalizaSucursal sucursal = sucursalList.get(position);
        String dis = UtilsLocation.getDistance2Points(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), new LatLng(sucursal.getLatitud(), sucursal.getLongitud()));

        holder.titleSucursal.setText(sucursal.getNombre() + " - " + dis);
        holder.description.setText(sucursal.getDireccion1());
    }

    @Override
    public int getItemCount() {
        return sucursalList.size();
    }

    /**
     * Encargada de Filtrar la lista del RecyclerView
     *
     * @param charText
     */
    public void filter(String charText) {
        // Pasamos cadena a LowerCase
        charText = charText.toLowerCase(Locale.getDefault());
        sucursalList.clear();
        // Si no existen caracteres agregamos los elementos
        if (charText.length() == 0) {
            sucursalList.addAll(arraylist);
        } else {
            // Recorremos el arreglo, si existe un nombre que coincida, agregamos a la lista
            for (DataLocalizaSucursal wp : arraylist) {
                if (wp.getNombre().toLowerCase(Locale.getDefault()).contains(charText)) {
                    sucursalList.add(wp);
                } else {
                 //   myInterfase.setOnSucursalesNull();
                }
            }
        }
        // Notificamso que los datos cambiaron
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleSucursal;
        public TextView description;

        public MyViewHolder(View itemView) {
            super(itemView);
            titleSucursal = (TextView) itemView.findViewById(R.id.sucursalTitle);
            description = (TextView) itemView.findViewById(R.id.sucursalDescription);
        }
    }
}
