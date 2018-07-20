package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoRequestPayment;
import com.pagatodo.yaganaste.ui_wallet.interfaces.RecyclerViewOnItemClickListener;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Armando Sandoval on 09/01/2018.
 */

public class RequestPaymentVerticalAdapter extends RecyclerView.Adapter<RequestPaymentVerticalAdapter.ViewHolder> {

    private List<DtoRequestPayment> data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    private float totalAmout;

    public RequestPaymentVerticalAdapter(@NonNull List<DtoRequestPayment> data, @NonNull RecyclerViewOnItemClickListener recyclerViewOnItemClickListener, float totalAmount) {
        this.data = data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
        this.totalAmout = totalAmount;
        data.add(createItemToAddFav());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_request_payment_vertical, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        data.get(position).setAmount(totalAmout / (data.size() - 1));
        DtoRequestPayment item = data.get(position);
        if (item.getIdFavorite() == -1) {
            holder.crlImageReq.setBorderColor(android.graphics.Color.parseColor(item.getColorBank()));
            GradientDrawable gd = createCircleDrawable(App.getContext().getResources().getColor(R.color.color_background_image_docs), android.graphics.Color.GRAY);
            holder.crlImageReq.setBackground(gd);
            holder.imgAddRequest.setBackground(App.getContext().getResources().getDrawable(R.drawable.new_fav_add));
            holder.txtNameReq.setText(item.getName());
            holder.txtNameReq.setTextColor(App.getContext().getResources().getColor(R.color.amout_color_req));
            holder.imgEditReq.setVisibility(View.GONE);
            holder.txtAmount.setVisibility(View.GONE);
            holder.txtInicialesReq.setVisibility(View.GONE);
        } else {
            holder.crlImageReq.setBorderColor(android.graphics.Color.parseColor(item.getColorBank()));
            if (item.getName().contains(" ")) {
                String shortName[] = item.getName().split(" ");
                try {
                    holder.txtNameReq.setText(shortName[0] + " " + shortName[1]);
                } catch (Exception e) {
                    holder.txtNameReq.setText(item.getName());
                }
            } else {
                holder.txtNameReq.setText(item.getName());
            }
            holder.txtNameReq.setTextColor(App.getContext().getResources().getColor(R.color.colorAccent));
            holder.txtAmount.setText(String.format("%s", StringUtils.getCurrencyValue(item.getAmount())));
            holder.txtAmount.setVisibility(View.VISIBLE);
            holder.imgEditReq.setVisibility(View.VISIBLE);
            if (item.getUrlImage().equals("")) {
                GradientDrawable gd = createCircleDrawable(android.graphics.Color.parseColor(item.getColorBank()),
                        android.graphics.Color.parseColor(item.getColorBank()));
                holder.crlImageReq.setBackground(gd);
                holder.txtInicialesReq.setVisibility(View.VISIBLE);
                String sIniciales = getIniciales(item.getName());
                holder.txtInicialesReq.setText(sIniciales);
            } else {
                holder.txtInicialesReq.setVisibility(View.GONE);
                setImagePicasoFav(holder.crlImageReq, item.getUrlImage());
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // Se encarga de crear el circulo Drawable que usaremos para mostrar las imagenes o los textos
    private GradientDrawable createCircleDrawable(int colorBackground, int colorBorder) {
        // Creamos el circulo que mostraremos
        int strokeWidth = 2; // 3px not dp
        int roundRadius = 140; // 8px not dp
        int strokeColor = colorBorder;
        int fillColor = colorBackground;
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        return gd;
    }

    /**
     * Obtiene las iniciales a mostrar si no tenemos foto: Ejemplo
     * Frank Manzo Nava= FM
     * Francisco = Fr
     *
     * @param fullName
     * @return
     */
    private String getIniciales(String fullName) {
        if (fullName.trim().length() == 1){
            return fullName.substring(0, 1).toUpperCase();
        }
        if (fullName.trim().length() > 1){
            return fullName.substring(0, 2).toUpperCase();
        }

        String[] spliName = fullName.split(" ");
        String sIniciales = "";
        if (spliName.length > 1) {
            sIniciales = spliName[0].substring(0, 1) + spliName[1].substring(0, 1).toUpperCase();
            return sIniciales;
        }
        return "";
    }

    private void setImagePicasoFav(ImageView imageView, String urlLogo) {
        Picasso.with(App.getContext())
                .load(urlLogo)
                .placeholder(R.mipmap.icon_user)
                .into(imageView);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener/*, View.OnLongClickListener */ {
        LinearLayout lytRow;
        CircleImageView crlImageReq;
        TextView txtInicialesReq, txtNameReq, txtAmount;
        ImageView imgEditReq, imgAddRequest;

        public ViewHolder(View itemView) {
            super(itemView);
            lytRow = (LinearLayout) itemView.findViewById(R.id.row_request_payment);
            crlImageReq = (CircleImageView) itemView.findViewById(R.id.crlImageReq);
            txtInicialesReq = (TextView) itemView.findViewById(R.id.txtInicialesReq);
            txtNameReq = (TextView) itemView.findViewById(R.id.txtNameReq);
            txtAmount = (TextView) itemView.findViewById(R.id.txtAmount);
            imgEditReq = (ImageView) itemView.findViewById(R.id.imgEditRequest);
            imgAddRequest = (ImageView) itemView.findViewById(R.id.imgAddRequest);
            lytRow.setOnClickListener(this);
            //itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }

        /*@Override
        public boolean onLongClick(View v) {
            recyclerViewOnItemClickListener.onLongClick(v, getAdapterPosition());
            return true;
        }*/
    }

    private DtoRequestPayment createItemToAddFav() {
        return new DtoRequestPayment(-1, "Agregar", "", "#747E84", "");
    }
}

