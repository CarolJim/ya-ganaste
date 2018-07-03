package com.pagatodo.yaganaste.ui_wallet.holders;

import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pagatodo.yaganaste.R;

public class InputDataViewHolder extends GenericHolder {

    private TextInputLayout inputLayout;
    private EditText editText;

    public InputDataViewHolder(View itemView) {
        super(itemView);
        init();
    }

    public TextInputLayout getInputLayout() {
        return this.inputLayout;
    }

    public EditText getEditText() {
        return this.editText;
    }

    @Override
    public void init() {
        this.inputLayout = this.itemView.findViewById(R.id.text_input);
        this.editText = this.itemView.findViewById(R.id.edit_text);
    }

    @Override
    public void bind(Object item, OnClickItemHolderListener listener) {
        String textHint = (String) item;
        this.inputLayout.setHint(textHint);
        this.editText.setOnFocusChangeListener((view, b) -> {
            if (b) {
                inputLayout.setBackgroundResource(R.drawable.inputtext_active);
            } else {
                inputLayout.setBackgroundResource(R.drawable.inputtext_normal);
            }
        });
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
