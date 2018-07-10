package com.pagatodo.yaganaste.ui_wallet.holders;

import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;

import com.pagatodo.yaganaste.ui_wallet.dto.DtoVideoTutorials;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

public class LinearTextViewHolder extends GenericHolder {

    private StyleTextView title;
    private StyleTextView SubTitle;

    public LinearTextViewHolder(View itemView) {
        super(itemView);
        init();
    }

    @Override
    public void init() {
       this.title = this.itemView.findViewById(R.id.title);
       this.SubTitle = this.itemView.findViewById(R.id.sub_title);
    }

    @Override
    public void bind(Object item, OnClickItemHolderListener listener) {
        DtoVideoTutorials videotutorial = (DtoVideoTutorials) item;
        this.title.setText(videotutorial.Title);
        this.SubTitle.setText(videotutorial.Subtitle);
        this.itemView.setOnClickListener(view -> listener.onItemClick(item));

    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(this.itemView);
    }

    @Override
    public View getView() {
        return this.itemView;
    }

}
