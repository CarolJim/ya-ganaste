package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.dto.DtoRequestPayment;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Armando Sandoval on 09/01/2018.
 */

public class RequestPaymentHorizontalAdapter extends RecyclerView.Adapter<RequestPaymentHorizontalAdapter.ViewHolder> {
    private List<DtoRequestPayment> data;

    public RequestPaymentHorizontalAdapter(@NonNull List<DtoRequestPayment> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_request_payment_horizontal, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DtoRequestPayment item = data.get(position);
        holder.crlImageReq.setBorderColor(android.graphics.Color.parseColor(item.getColorBank()));
        holder.txtNameReq.setText(item.getName());
        holder.txtAmountReq.setText(String.format("%s", StringUtils.getCurrencyValue(item.getAmount())));
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

    public DtoRequestPayment getItem(int position){
        return data.get(position);
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

        String[] spliName = fullName.split(" ");
        String sIniciales = "";
        if (spliName.length > 1) {
            sIniciales = spliName[0].substring(0, 1) + spliName[1].substring(0, 1).toUpperCase();
            return sIniciales;
        }

        if (fullName.trim().length() > 1){
            return fullName.substring(0, 2).toUpperCase();
        }
        return "";
    }

    private void setImagePicasoFav(ImageView imageView, String urlLogo) {
        Picasso.get().load(urlLogo).placeholder(R.mipmap.icon_user_fail)
                .error(R.mipmap.icon_user_fail)
                .into(imageView);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView crlImageReq;
        TextView txtInicialesReq, txtNameReq, txtAmountReq;

        public ViewHolder(View itemView) {
            super(itemView);
            crlImageReq = (CircleImageView) itemView.findViewById(R.id.crlImageReq);
            txtInicialesReq = (TextView) itemView.findViewById(R.id.txtInicialesReq);
            txtNameReq = (TextView) itemView.findViewById(R.id.txtNameReq);
            txtAmountReq = (TextView) itemView.findViewById(R.id.txtAmountReq);
        }

    }


}

