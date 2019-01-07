package com.pagatodo.view_manager.holders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.GenericHolder;
import com.pagatodo.view_manager.controllers.OnHolderListener;
import com.pagatodo.view_manager.controllers.dataholders.LabelArrowDataHolder;

public class LabelArrowHolder extends GenericHolder<LabelArrowDataHolder> {

    private TextView textTitle;
    private TextView textSubtitle;

    public LabelArrowHolder(@NonNull View itemView) {
        super(itemView);
        init();
    }

    @Override
    public void init() {
        this.textTitle = this.itemView.findViewById(R.id.lable_title);
        this.textSubtitle = this.itemView.findViewById(R.id.label_subtitle);
    }

    @Override
    public void bind(LabelArrowDataHolder item, OnHolderListener<LabelArrowDataHolder> listener) {
        this.textTitle.setText(item.getLabelTitle());
        this.textSubtitle.setText(item.getLabelSubtitle());
    }
}
