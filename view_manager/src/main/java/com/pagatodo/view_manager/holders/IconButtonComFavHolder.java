package com.pagatodo.view_manager.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.GenericHolder;
import com.pagatodo.view_manager.controllers.OnHolderListener;
import com.pagatodo.view_manager.controllers.dataholders.IconButtonDataHolder;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;

public class IconButtonComFavHolder extends GenericHolder<IconButtonDataHolder> {

    private ImageView imageCom;
    private TextView name;
    private TextView nameComp;

    public IconButtonComFavHolder(@NonNull View itemView) {
        super(itemView);
        init();
    }

    @Override
    public void init() {
        this.imageCom = this.itemView.findViewById(R.id.icon_image);
        this.name = this.itemView.findViewById(R.id.text_name);
        this.nameComp = this.itemView.findViewById(R.id.text_name_com);
    }

    @Override
    public void bind(IconButtonDataHolder item, OnHolderListener<IconButtonDataHolder> listener) {
        if (item.getImageUrl() != null) {
            if (!item.getImageUrl().isEmpty()) {
                Picasso.get().load(itemView.getContext().getString(R.string.url_images_logos) + item.getImageUrl())
                        .into(this.imageCom);
                this.name.setVisibility(View.VISIBLE);
                this.name.setText(item.getName());
                this.nameComp.setText(item.getNamCom());
            }
        }
    }
}
