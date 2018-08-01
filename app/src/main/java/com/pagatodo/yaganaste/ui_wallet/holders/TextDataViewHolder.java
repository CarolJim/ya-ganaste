package com.pagatodo.yaganaste.ui_wallet.holders;

import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.pojos.TextData;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

public class TextDataViewHolder extends GenericHolder {

    public StyleTextView leftText;
    public StyleTextView rightText;

    public TextDataViewHolder(View itemView) {
        super(itemView);
        init();
    }


    @Override
    public void init() {
        this.leftText = itemView.findViewById(R.id.txt_left);
        this.rightText = itemView.findViewById(R.id.txt_right);
    }

    @Override
    public void bind(Object item, OnClickItemHolderListener listener) {
        TextData textData = (TextData) item;
        this.leftText.setText(textData.getTitleLeft());
        this.rightText.setText(textData.getTitleRight());
        this.rightText.setSelected(true);
    }

    @Override
    public View getView() {
        return this.itemView;
    }

    @Override
    public void inflate(ViewGroup layout) {

    }
}
