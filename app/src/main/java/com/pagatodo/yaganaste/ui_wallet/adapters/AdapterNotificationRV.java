package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataListaNotificationArray;
import com.pagatodo.yaganaste.interfaces.INotificationHistory;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by FranciscoManzo on 12/02/2018.
 * Adapter de RV para las notificaciones
 */

public class AdapterNotificationRV extends RecyclerView.Adapter<AdapterNotificationRV.ViewHolder> {
    ArrayList<DataListaNotificationArray> myDataset;
    INotificationHistory mView;

    public AdapterNotificationRV(INotificationHistory mView, ArrayList<DataListaNotificationArray> myDataset) {
        this.mView = mView;
        this.myDataset = myDataset;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTittle, mDesc;
        public CircleImageView imageViewBorder ;

        public ViewHolder(View itemView) {
            super(itemView);
            mTittle = itemView.findViewById(R.id.adapter_notification_tittle);
            mDesc = itemView.findViewById(R.id.adapter_notification_desc);
            imageViewBorder = itemView.findViewById(R.id.imgItemGalleryMark);
        }
    }

    @Override
    public AdapterNotificationRV.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_notification_rv_item, parent, false);
        AdapterNotificationRV.ViewHolder vh = new AdapterNotificationRV.ViewHolder(v);
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
        holder.mTittle.setText(myDataset.get(position).getIdNotificacion() + " " +myDataset.get(position).getTitulo());
        holder.mDesc.setText(myDataset.get(position).getMensaje());

      /*  int mImagenUrl = myDataset.get(position).getLogo();
        if(mImagenUrl > 0){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.imageViewBorder.setBackground(App.getContext().getDrawable(mImagenUrl));
            }
        }*/

        // Bloques para mostrar los items ya leidos
        if(myDataset.get(position).isLeido()){

        holder.mTittle.setTextColor(Color.parseColor("#aebfc5"));
        holder.mDesc.setTextColor(Color.parseColor("#aebfc5"));
        }
    }

    @Override
    public int getItemCount() {
        return myDataset.size();
    }
}

/**
 CircleImageView
 imgItemGalleryMark

 ImageView
 imgItemGalleryPay

 TExtView
 textIniciales
 **/