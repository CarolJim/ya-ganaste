package com.pagatodo.view_manager.wallet.holders;

import android.view.View;
import android.widget.TextView;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.GenericHolder;
import com.pagatodo.view_manager.controllers.OnHolderListener;
import com.pagatodo.view_manager.wallet.data.YaGanasteCardData;

import androidx.annotation.NonNull;

public class YaGanastaCardHolder extends GenericHolder<YaGanasteCardData> {

    private TextView numberCard;
    private TextView nameOwner;

    public YaGanastaCardHolder(@NonNull View itemView) {
        super(itemView);
        init();
    }

    @Override
    public void init() {
        this.numberCard = this.itemView.findViewById(R.id.text_number_card);
        this.nameOwner = this.itemView.findViewById(R.id.text_name_owner);
    }

    @Override
    public void bind(YaGanasteCardData item, OnHolderListener<YaGanasteCardData> listener) {
        this.numberCard.setText(hideNumberCard(item.getNumberCard()));
        this.nameOwner.setText(item.getNameOwner());

        if (listener != null){
            this.itemView.setOnClickListener(v -> listener.onClickView(item,itemView));
        }
    }

    private String hideNumberCard(String numberCard){
        String hide = "**** **** **** ";
        if (!numberCard.trim().isEmpty() && numberCard.length() == 16){
            hide = hide + numberCard.substring(12);
        } else {
            hide = hide + "0000";
        }

        return hide;
    }
}
