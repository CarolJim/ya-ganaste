package com.pagatodo.yaganaste.ui_wallet.adapters;


import android.location.Location;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

import java.text.NumberFormat;
import java.util.ArrayList;

public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.ViewHolder> {

    private ArrayList<BranchList> listShops;

    private Location myLocation;

    public ShopsAdapter(ArrayList<BranchList> listShops ,Location location) {
        this.listShops = listShops;
        this.myLocation=location;
    }
    public ShopsAdapter(ArrayList<BranchList> listShops ) {
        this.listShops = listShops;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_sucursales_promo, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        BranchList elemnt= listShops.get(i);


        float distanceto=11.09f;
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(1);
        format.setMinimumFractionDigits(1);
        String output=format.format(distanceto);
        viewHolder.txt_name_branch.setText(elemnt.getName());
        viewHolder.txt_address_branch.setText(elemnt.getAddress());
        viewHolder.txt_distance_branch.setText(output+" Km");


    }

    @Override
    public int getItemCount() {
        return listShops.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        StyleTextView txt_name_branch, txt_arrive_branch;
        TextView txt_address_branch, txt_distance_branch;

        public ViewHolder(View itemView) {
            super(itemView);
            //textView= (GenericTextView) itemView.findViewById(R.id.txt_row_dialog_list);
            txt_name_branch = (StyleTextView) itemView.findViewById(R.id.name_branch);
            txt_address_branch = (TextView) itemView.findViewById(R.id.address_branch);
            txt_distance_branch = (TextView) itemView.findViewById(R.id.distance_branch);
            txt_arrive_branch = (StyleTextView) itemView.findViewById(R.id.arrive_branch);

        }
    }
}