package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.ui_wallet.interfaces.RecyclerViewOnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Armando Sandoval on 09/01/2018.
 */

public class FavoritesRequestPaymentAdapter extends RecyclerView.Adapter<FavoritesRequestPaymentAdapter.PaletteViewHolder> {
    private List<Favoritos> data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public FavoritesRequestPaymentAdapter(@NonNull List<Favoritos> data, @NonNull RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        this.data = data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @Override
    public PaletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_envios, parent, false);
        return new PaletteViewHolder(row);
    }

    @Override
    public void onBindViewHolder(PaletteViewHolder holder, int position) {
        Favoritos item = data.get(position);
        holder.crlImageFavorite.setBorderColor(android.graphics.Color.parseColor(item.getColorMarca()));
        holder.txtNameFav.setText(item.getNombre());
        if (item.getImagenURL().equals("")) {
            GradientDrawable gd = createCircleDrawable(android.graphics.Color.parseColor(item.getColorMarca()),
                    android.graphics.Color.parseColor(item.getColorMarca()));
            holder.crlImageFavorite.setBackground(gd);
            holder.txtInicialesFav.setVisibility(View.VISIBLE);
            String sIniciales = getIniciales(item.getNombre());
            holder.txtInicialesFav.setText(sIniciales);
        } else {
            holder.txtInicialesFav.setVisibility(View.GONE);
            setImagePicasoFav(holder.crlImageFavorite, item.getImagenURL());
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
        Picasso.get()
                .load(urlLogo)
                .placeholder(R.mipmap.icon_user_fail)
                .error(R.mipmap.icon_user_fail)
                .into(imageView);
    }

    class PaletteViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener, View.OnLongClickListener {
        CircleImageView crlImageFavorite;
        ImageView imgAddFavorite;
        TextView txtInicialesFav, txtNameFav;
        LinearLayout lytFavorite;

        public PaletteViewHolder(View itemView) {
            super(itemView);
            lytFavorite = (LinearLayout) itemView.findViewById(R.id.lyt_favorite_item);
            crlImageFavorite = (CircleImageView) itemView.findViewById(R.id.crlImageFavorite);
            imgAddFavorite = (ImageView) itemView.findViewById(R.id.imgAddFavorite);
            txtInicialesFav = (TextView) itemView.findViewById(R.id.txtInicialesFav);
            txtNameFav = (TextView) itemView.findViewById(R.id.txtNameFav);
            lytFavorite.setOnClickListener(this);
            //itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            recyclerViewOnItemClickListener.onLongClick(v, getAdapterPosition());
            return true;
        }
    }

}

