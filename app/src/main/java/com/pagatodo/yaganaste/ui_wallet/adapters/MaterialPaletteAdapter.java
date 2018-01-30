package com.pagatodo.yaganaste.ui_wallet.adapters;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.DataFavoritos;
import com.pagatodo.yaganaste.ui_wallet.interfaces.RecyclerViewOnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Armando Sandoval on 09/01/2018.
 */

public class MaterialPaletteAdapter extends RecyclerView.Adapter<MaterialPaletteAdapter.PaletteViewHolder> {
    private List<DataFavoritos> data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public MaterialPaletteAdapter(List<DataFavoritos> data, RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
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
        DataFavoritos item = data.get(position);

        if (item.getIdComercio() == 0) {
            holder.crlImageFavorite.setBorderColor(android.graphics.Color.parseColor(item.getColorMarca()));
            GradientDrawable gd = createCircleDrawable(App.getContext().getResources().getColor(R.color.color_background_image_docs), android.graphics.Color.GRAY);
            holder.crlImageFavorite.setBackground(gd);
            holder.imgAddFavorite.setBackground(App.getContext().getResources().getDrawable(R.drawable.new_fav_add));
            holder.txtNameFav.setText("Agregar");
            holder.txtInicialesFav.setVisibility(View.GONE);
        } else {
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
        String[] spliName = fullName.split(" ");
        String sIniciales = "";
        if (spliName.length > 1) {
            sIniciales = spliName[0].substring(0, 1) + spliName[1].substring(0, 1).toUpperCase();
        } else {
            sIniciales = fullName.substring(0, 2).toUpperCase();
        }
        return sIniciales;
    }

    private void setImagePicasoFav(ImageView imageView, String urlLogo) {
        Picasso.with(App.getContext())
                .load(urlLogo)
                .placeholder(R.mipmap.icon_user)
                .error(R.mipmap.icon_user)
                .into(imageView);
    }

    class PaletteViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener, View.OnLongClickListener {
        CircleImageView crlImageFavorite;
        ImageView imgAddFavorite;
        TextView txtInicialesFav, txtNameFav;


        public PaletteViewHolder(View itemView) {
            super(itemView);
            crlImageFavorite = (CircleImageView) itemView.findViewById(R.id.crlImageFavorite);
            imgAddFavorite = (ImageView) itemView.findViewById(R.id.imgAddFavorite);
            txtInicialesFav = (TextView) itemView.findViewById(R.id.txtInicialesFav);
            txtNameFav = (TextView) itemView.findViewById(R.id.txtNameFav);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
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

