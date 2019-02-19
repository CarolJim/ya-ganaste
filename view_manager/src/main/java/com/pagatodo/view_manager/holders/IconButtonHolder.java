package com.pagatodo.view_manager.holders;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.components.ImageInitials;
import com.pagatodo.view_manager.controllers.GenericHolder;
import com.pagatodo.view_manager.controllers.OnHolderListener;
import com.pagatodo.view_manager.controllers.dataholders.IconButtonDataHolder;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;


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
        setIconImage(item.getIconRes());
        setTextName(item.getName());
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

}
