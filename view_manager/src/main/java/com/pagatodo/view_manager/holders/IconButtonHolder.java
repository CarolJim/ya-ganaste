package com.pagatodo.view_manager.holders;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.CircleTransform;
import com.pagatodo.view_manager.controllers.GenericHolder;
import com.pagatodo.view_manager.controllers.OnHolderListener;
import com.pagatodo.view_manager.controllers.dataholders.IconButtonDataHolder;
import com.squareup.picasso.Picasso;


public class IconButtonHolder extends GenericHolder<IconButtonDataHolder> {

    private ImageView iconImage;
    private TextView textName;

    public IconButtonHolder(@NonNull View itemView) {
        super(itemView);
        init();
    }

    @Override
    public void init() {
        this.iconImage = this.itemView.findViewById(R.id.icon_image);
        this.textName = this.itemView.findViewById(R.id.text_name);
    }

    @Override
    public void bind(IconButtonDataHolder item, final OnHolderListener<IconButtonDataHolder> listener) {
        if (item.getImageUrl() != null){
            if (!item.getImageUrl().isEmpty()){
                Picasso.get().load(itemView.getContext().getString(R.string.url_images_logos) + item.getImageUrl())
                        .transform(new CircleTransform()).into(this.iconImage);
            }
        } else {
            this.iconImage.setImageDrawable(item.getIconRes());

        }

        this.textName.setText(item.getName());
        if (listener != null) {
            this.itemView.setOnClickListener(view -> listener.onClickView(item, null));
        }
    }

    public void setIconImage(Drawable res){
        this.iconImage.setImageDrawable(res);
    }

    public void setTextName(String textName){
        this.textName.setText(textName);
    }


    /*public void setOnClickHolderListener(final OnHolderListener<IconButtonDataHolder> listener){
        if (listener != null){
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickView(item,null);
                }
            });
        }
    }*/
}
