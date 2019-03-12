package com.pagatodo.view_manager.wallet.holders;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.GenericHolder;
import com.pagatodo.view_manager.controllers.LauncherView;
import com.pagatodo.view_manager.controllers.OnHolderListener;
import com.pagatodo.view_manager.wallet.data.ReaderDeviceData;

import androidx.annotation.NonNull;

public class ReaderDeviceCardHolder implements LauncherView {

    private ReaderDeviceData item;
    private View itemView;

    private ImageView resDrawable;

    public ReaderDeviceCardHolder(ReaderDeviceData item, View itemView) {
        this.item = item;
        this.itemView = itemView;
        init();
    }

    @Override
    public void init() {
        this.resDrawable = this.itemView.findViewById(R.id.img_reader_divece);
    }

    @Override
    public void bind() {
        if (item.getResImg()!= null){
            this.resDrawable.setImageDrawable(item.getResImg());
        }
        /*if (listener!= null){
            itemView.setOnClickListener(v -> listener.onClickView(item,itemView));
        }*/
    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(itemView);
    }

    @Override
    public View getView() {
        return itemView;
    }

}
