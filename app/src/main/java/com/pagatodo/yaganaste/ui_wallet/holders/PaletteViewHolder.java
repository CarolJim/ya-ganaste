package com.pagatodo.yaganaste.ui_wallet.holders;

import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.ui_wallet.interfaces.RecyclerViewOnItemClickListener;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PaletteViewHolder extends RecyclerView.ViewHolder {

    private CircleImageView crlImageFavorite;
    private CircleImageView crlImageStatus;
    private ImageView imgAddFavorite;
    private TextView txtInicialesFav, txtNameFav;
    private View itemView;

    public PaletteViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        crlImageFavorite = itemView.findViewById(R.id.crlImageFavorite);
        crlImageStatus = itemView.findViewById(R.id.imgItemGalleryStatus);
        imgAddFavorite = itemView.findViewById(R.id.imgAddFavorite);
        txtInicialesFav = itemView.findViewById(R.id.txtInicialesFav);
        txtNameFav = itemView.findViewById(R.id.txtNameFav);
    }

    public void bind(final Favoritos favorito, final OnClickListener listener){

        imgAddFavorite.setVisibility(View.GONE);
        txtNameFav.setText(noSpaces(favorito.getNombre()));
        if (!favorito.getImagenURL().isEmpty()) {
            setImagePicasoFav(crlImageFavorite, favorito.getImagenURL());
        } else {
            GradientDrawable gd = createCircleDrawable(android.graphics.Color.parseColor(favorito.getColorMarca()),
                        android.graphics.Color.parseColor(favorito.getColorMarca()));
            crlImageFavorite.setBackground(gd);
            txtInicialesFav.setVisibility(View.VISIBLE);
            txtInicialesFav.setText(StringUtils.getIniciales(favorito.getNombre()));
        }
        this.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(favorito);
            }
        });
    }

    public void edition(boolean editable){
        if (editable){
            crlImageStatus.setImageDrawable(ContextCompat.getDrawable(App.getContext(), R.drawable.edit_icon));
            crlImageStatus.setVisibility(View.VISIBLE);
        } else {
            crlImageStatus.setVisibility(View.GONE);
        }
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

    private void setImagePicasoFav(ImageView imageView, String urlLogo) {
        Picasso.with(imageView.getContext())
                .load(urlLogo)
                .into(imageView);
    }

    private String noSpaces(String cad){
        String names;
        if (cad.contains(" ")) {
            String[] snames = cad.split(" ");
            names = snames[0];
        } else {
            names = cad;
        }
        return names;
    }

    public interface OnClickListener {
        void onClick(Favoritos favorito);
    }
}
