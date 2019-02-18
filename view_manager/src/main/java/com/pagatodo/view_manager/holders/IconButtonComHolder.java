package com.pagatodo.view_manager.holders;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.GenericHolder;
import com.pagatodo.view_manager.controllers.OnHolderListener;
import com.pagatodo.view_manager.controllers.dataholders.IconButtonDataHolder;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;

public class IconButtonComHolder extends GenericHolder<IconButtonDataHolder> {

    private ImageView iconImage;
    private TextView textNameCom;

    public IconButtonComHolder(@NonNull View itemView) {
        super(itemView);
        init();
    }

    @Override
    public void init() {
        this.iconImage = this.itemView.findViewById(R.id.icon_image);
        this.textNameCom = this.itemView.findViewById(R.id.text_name_com);
    }

    @Override
    public void bind(IconButtonDataHolder item, final OnHolderListener<IconButtonDataHolder> listener) {
        if (item.getImageUrl() != null) {
            if (!item.getImageUrl().isEmpty()) {
                Picasso.get().load(itemView.getContext().getString(R.string.url_images_logos) + item.getImageUrl())
                        .into(this.iconImage);
            }
        }
        this.textNameCom.setText(item.getNamCom());
        if (listener != null){
            itemView.setOnClickListener(v -> listener.onClickView(item,itemView));
        }
    }

    public void setIconImage(Drawable res){
        this.iconImage.setImageDrawable(res);
    }

    public void setTextName(String textName){
        this.textNameCom.setText(textName);
    }
}
