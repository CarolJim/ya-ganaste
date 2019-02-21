package com.pagatodo.yaganaste.modules.emisor.PaymentToQR.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.modules.data.QrItems;
import com.pagatodo.yaganaste.modules.patterns.GenericHolder;
import com.pagatodo.yaganaste.modules.patterns.OnHolderListener;

import java.util.ArrayList;

public class QRAdapter extends RecyclerView.Adapter<QRAdapter.ViewHolder> {

    private ArrayList<QrItems> listQR;
    private OnHolderListener<QrItems> listener;


    public QRAdapter(OnHolderListener<QrItems> listener) {
        this.listQR = new ArrayList<>();
        this.listener = listener;
    }


    public void setQrUser(ArrayList<QrItems> qrUser) {
        this.listQR = qrUser;
        notifyDataSetChanged();
    }

    @Override
    public QRAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_my_qr, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QRAdapter.ViewHolder holder, int position) {
        /*MyQrData element=listQR.get(position);
        holder.txtNameQr.setText(element.getNameQR());
        holder.imgQR.setImageResource(element.getQR());
        holder.txtLastNumberQR.setText(element.getNumberQR());*/
        QrItems element = listQR.get(position);

//        holder.bind(listQR.get(position),this.listener);

        holder.txtNameQr.setText(element.getQrUser().getAlias());
        holder.txtLastNumberQR.setText(element.getQrUser().getPlate().substring(8));
        holder.container_QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickItem(element);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listQR.size();
    }


    class ViewHolder extends GenericHolder<QrItems> {
        private TextView txtNameQr, txtLastNumberQR;
        private ImageView imgQr;
        private LinearLayout container_QR;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            init();
        }

        @Override
        public void init() {
            this.txtNameQr = (TextView) itemView.findViewById(R.id.name_qr);
            this.imgQr = (ImageView) itemView.findViewById(R.id.image_qr);
            this.txtLastNumberQR = (TextView) itemView.findViewById(R.id.last_number_qr);
            this.container_QR = (LinearLayout) itemView.findViewById(R.id.container_qr);
        }

        @Override
        public void bind(QrItems item, OnHolderListener<QrItems> listener) {
            if (item.getResImage() == R.drawable.qr_code) {
                this.imgQr.setImageResource(item.getResImage());
                this.txtNameQr.setText(item.getQrUser().getAlias());
                if (!item.getQrUser().getPlate().isEmpty()) {
                    this.txtLastNumberQR.setText(item.getQrUser().getPlate().substring(8));
                }
            } else {
                this.imgQr.setImageResource(item.getResImage());
                this.imgQr.setScaleType(ImageView.ScaleType.CENTER);
                this.imgQr.setBackgroundResource(R.drawable.border_cutter);
                this.txtLastNumberQR.setText(item.getQrUser().getPlate());
            }

            if (listener != null) {
                this.itemView.setOnClickListener(view -> listener.onClickItem(item));
            }
        }
    }


}
