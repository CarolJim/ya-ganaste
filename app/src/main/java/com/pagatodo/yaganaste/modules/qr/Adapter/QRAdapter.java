package com.pagatodo.yaganaste.modules.qr.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.qr.MyQrData;

import java.util.ArrayList;

public class QRAdapter extends RecyclerView.Adapter<QRAdapter.ViewHolder> {

    public QRAdapter(ArrayList<MyQrData> listQR){
        this.listQR=listQR;
    }

    private ArrayList<MyQrData> listQR;

    @Override
    public QRAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_my_qr,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QRAdapter.ViewHolder holder, int position) {
        MyQrData element=listQR.get(position);
        holder.txtNameQr.setText(element.getNameQR());
        holder.imgQR.setImageResource(element.getQR());
        holder.txtLastNumberQR.setText(element.getNumberQR());
    }

    @Override
    public int getItemCount() {
        return listQR.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtNameQr,txtLastNumberQR;
        ImageView imgQR;
        public ViewHolder(View itemView) {
            super(itemView);
            txtNameQr=(TextView)itemView.findViewById(R.id.name_qr);
            imgQR=(ImageView) itemView.findViewById(R.id.image_qr);
            txtLastNumberQR=(TextView)itemView.findViewById(R.id.last_number_qr);
        }
    }
}
