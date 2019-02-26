package com.pagatodo.yaganaste.modules.newsend.AllFavorites;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;
import com.pagatodo.yaganaste.ui._adapters.OnRecyclerItemClickListener;
import com.pagatodo.yaganaste.utils.StringUtils;
import com.pagatodo.yaganaste.utils.customviews.carousel.CarouselItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterSelecFavoNew extends RecyclerView.Adapter<AdapterSelecFavoNew.ViewHolder> {

    List<Favoritos> backUpResponseFavoritos;
    Activity activity;
    ArrayList<CarouselItem> backUpResponse;
    CircleImageView crlImageFavorite;
    TextView txtInicialesFav;
    private ImageView imgAddFavorite;
    private IReciclerfavoritos listener;


    public AdapterSelecFavoNew(List<Favoritos> backUpResponseFavoritos, Activity activity, ArrayList<CarouselItem> backUpResponse, IReciclerfavoritos listener) {
        this.backUpResponseFavoritos = backUpResponseFavoritos;
        this.activity = activity;
        this.backUpResponse = backUpResponse;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rowfavolist, viewGroup, false);
        crlImageFavorite = (CircleImageView) view.findViewById(R.id.crlImageFavorite);
        txtInicialesFav = (TextView) view.findViewById(R.id.txtInicialesFav);
        imgAddFavorite = (ImageView) view.findViewById(R.id.imgAddFavorite);
        return new AdapterSelecFavoNew.ViewHolder(view);
    }
    public void filterList(ArrayList<Favoritos> filter){
        this.backUpResponseFavoritos=filter;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final int posicion = i;
        final Favoritos favorito = backUpResponseFavoritos.get(i);
        String bancoshow = "";
        for (CarouselItem carouselItem : backUpResponse) {
            if (favorito.getIdComercio() == carouselItem.getComercio().getIdComercio()) {
                bancoshow = carouselItem.getComercio().getNombreComercio();
            }

        }

        bancoshow = bancoshow + "-" + getleastfourt(favorito.getReferencia());


        viewHolder.titulo.setText(favorito.getNombre());
        viewHolder.subtitulo.setText(bancoshow);
        imgAddFavorite.setVisibility(View.GONE);

        if (!favorito.getImagenURL().isEmpty()) {
            setImagePicasoFav(crlImageFavorite, favorito.getImagenURL());
        } else {
            GradientDrawable gd = createCircleDrawable(android.graphics.Color.parseColor(favorito.getColorMarca()),
                    android.graphics.Color.parseColor(favorito.getColorMarca()));
            crlImageFavorite.setBackground(gd);
            txtInicialesFav.setVisibility(View.VISIBLE);
            txtInicialesFav.setText(StringUtils.getIniciales(favorito.getNombre()));
        }

        viewHolder.llyfavo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //listener.onRecyclerItemClick(view,posicion,dtoStates);
                listener.onRecyclerItemClick(view, posicion, false, favorito);
            }
        });
        viewHolder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //listener.onRecyclerItemClick(view,posicion,dtoStates);
                listener.onRecyclerItemClick(view, posicion, true, favorito);
            }
        });
    }

    private String getleastfourt(String word) {
        if (word.length() == 4) {
            return word;
        } else if (word.length() > 4) {
            return word.substring(word.length() - 4);
        } else {
            // whatever is appropriate in this case
            throw new IllegalArgumentException("word has less than 3 characters!");
        }

    }

    private void setImagePicasoFav(ImageView imageView, String urlLogo) {
        Picasso.get()
                .load(urlLogo)
                .into(imageView);
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

    @Override
    public int getItemCount() {
        return backUpResponseFavoritos.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, subtitulo;
        ImageView img_edit;
        LinearLayout llyfavo;

        public ViewHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.txtNameFav);
            subtitulo = (TextView) itemView.findViewById(R.id.txtNameComerce);
            llyfavo = (LinearLayout) itemView.findViewById(R.id.llyfavo);
            img_edit = (ImageView) itemView.findViewById(R.id.img_edit);
        }
    }

}
