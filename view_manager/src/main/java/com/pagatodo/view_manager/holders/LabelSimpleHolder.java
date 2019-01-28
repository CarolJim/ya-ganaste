package com.pagatodo.view_manager.holders;

import android.view.View;
import android.widget.TextView;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.GenericHolder;
import com.pagatodo.view_manager.controllers.OnHolderListener;
import com.pagatodo.view_manager.controllers.dataholders.LabelDataHolder;

import androidx.annotation.NonNull;

public class LabelSimpleHolder extends GenericHolder<LabelDataHolder> {

    private TextView labelTitle;

    public LabelSimpleHolder(@NonNull View itemView) {
        super(itemView);
        init();
    }

    @Override
    public void init() {
        this.labelTitle = itemView.findViewById(R.id.lable_title);
    }

    @Override
    public void bind(LabelDataHolder item, OnHolderListener<LabelDataHolder> listener) {
        if (item != null){
            this.labelTitle.setText(item.getLabelTitle());
        }
    }
}
