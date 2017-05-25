package com.pagatodo.yaganaste.ui.maintabs.adapters;

import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataLocalizaSucursal;
import com.pagatodo.yaganaste.utils.UtilsLocation;

import java.util.List;

/**
 * Created by Jordan on 23/05/2017.
 */

public class RecyclerSucursalesAdapter extends RecyclerView.Adapter<RecyclerSucursalesAdapter.MyViewHolder>{

    private List<DataLocalizaSucursal> sucursalList;
    private Location myLocation;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView titleSucursal;
        public TextView description;

        public MyViewHolder(View itemView) {
            super(itemView);
            titleSucursal = (TextView)itemView.findViewById(R.id.sucursalTitle);
            description = (TextView)itemView.findViewById(R.id.sucursalDescription);
        }
    }

    public RecyclerSucursalesAdapter(List<DataLocalizaSucursal> l, Location location){
        this.sucursalList = l;
        this.myLocation = location;
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
}