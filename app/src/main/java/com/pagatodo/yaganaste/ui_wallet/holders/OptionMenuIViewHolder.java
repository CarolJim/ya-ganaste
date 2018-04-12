package com.pagatodo.yaganaste.ui_wallet.holders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.pojos.OptionMenuItem;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

public class OptionMenuIViewHolder extends GenericHolder {

    private RelativeLayout relativeLayout;
    public ImageView imageView;
    public StyleTextView title;
    public View dividerList;

    public OptionMenuIViewHolder(View itemView) {
        super(itemView);
        this.init();

    }

    @Override
    public void init() {
        this.relativeLayout = itemView.findViewById(R.id.item_menu);
        this.imageView = itemView.findViewById(R.id.ic_item);
        this.title = itemView.findViewById(R.id.title);
        this.dividerList = itemView.findViewById(R.id.dividerList);
    }

    @Override
    public void bind(final Object item, final OnClickItemHolderListener listener) {
        final OptionMenuItem itemOption = (OptionMenuItem) item;

        this.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(item);
            }
        });

        if (itemOption.getResourceItem() != -1){
            this.imageView.setBackgroundResource(itemOption.getResourceItem());
        }

        this.title.setText(itemOption.getResourceTitle());

        if (itemOption.isDivider()) {
            dividerList.setVisibility(View.VISIBLE);
        } else {
            dividerList.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public View getView() {
        return this.imageView;
    }
}
