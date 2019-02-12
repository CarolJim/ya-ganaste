package com.pagatodo.yaganaste.ui_wallet.holders;

import android.graphics.drawable.GradientDrawable;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PaletteViewHolder extends GenericHolder {

    private CircleImageView crlImageFavorite;
    private CircleImageView crlImageStatus;
    private ImageView imgAddFavorite;
    private TextView txtInicialesFav, txtNameFav;

    public PaletteViewHolder(View itemView) {
        super(itemView);
        init();
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
        Picasso.get()
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

    @Override
    public void init() {
        this.crlImageFavorite = itemView.findViewById(R.id.crlImageFavorite);
        this.crlImageStatus = itemView.findViewById(R.id.imgItemGalleryStatus);
        this.imgAddFavorite = itemView.findViewById(R.id.imgAddFavorite);
        this.txtInicialesFav = itemView.findViewById(R.id.txtInicialesFav);
        this.txtNameFav = itemView.findViewById(R.id.txtNameFav);


    }

    @Override
    public void bind(Object item, OnClickItemHolderListener listener) {
        Favoritos favorito = (Favoritos) item;
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
        this.itemView.setOnClickListener(view -> listener.onItemClick(favorito));
    }

    @Override
    public void inflate(ViewGroup layout) {

    }

    @Override
    public View getView() {
        return null;
    }
}
