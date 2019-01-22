package com.pagatodo.view_manager.holders;

import androidx.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.pagatodo.view_manager.R;
import com.pagatodo.view_manager.controllers.GenericHolder;
import com.pagatodo.view_manager.controllers.OnHolderListener;
import com.pagatodo.view_manager.controllers.dataholders.LabelArrowCheckboxDataHolder;


public class LabelArrowCheckHolder extends GenericHolder<LabelArrowCheckboxDataHolder> {

    private TextView textTitle;
    private TextView textSubtitle;
    private CheckBox check;

    public LabelArrowCheckHolder(@NonNull View itemView) {
        super(itemView);
        init();
    }

    @Override
    public void init() {
        this.textTitle = this.itemView.findViewById(R.id.lable_title);
        this.textSubtitle = this.itemView.findViewById(R.id.label_subtitle);
        this.check = this.itemView.findViewById(R.id.check);
    }

    @Override
    public void bind(LabelArrowCheckboxDataHolder item, OnHolderListener<LabelArrowCheckboxDataHolder> listener) {
        this.textTitle.setText(item.getLabelTitle());
        this.textSubtitle.setText(item.getLabelSubtitle());
    }

    public CheckBox getCheckbox(){
        return this.check;
    }
}
