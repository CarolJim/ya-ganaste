package com.pagatodo.view_manager.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.GenericHolder;
import com.pagatodo.view_manager.controllers.OnHolderListener;
import com.pagatodo.view_manager.controllers.dataholders.RowFavDataHolder;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;

public class FavoriteHorizontalHolder extends GenericHolder<RowFavDataHolder> {

    private ImageView urlImage;
    private TextView name;
    private TextView reference;
    private ImageView pencil;

    public FavoriteHorizontalHolder(@NonNull View itemView) {
        super(itemView);
        init();
    }

    @Override
    public void init() {
        this.urlImage = this.itemView.findViewById(R.id.image_url);
        this.name = this.itemView.findViewById(R.id.txtNameFav);
        this.reference = this.itemView.findViewById(R.id.txtNameComerce);
        this.pencil = this.itemView.findViewById(R.id.pencil);
    }

    @Override
    public void bind(RowFavDataHolder item, final OnHolderListener<RowFavDataHolder> listener) {
        if (item.getImageUrl() != null){
            if (!item.getImageUrl().isEmpty()){
                Picasso.get().load(itemView.getContext().getString(R.string.url_images_logos) +
                        item.getImageUrl()).into(urlImage);
            }
        }
        this.name.setText(item.getName());
        this.reference.setText(item.getRef());
        if (listener != null){
            this.pencil.setOnClickListener(v -> listener.onClickView(item,itemView));
        }
    }
}
