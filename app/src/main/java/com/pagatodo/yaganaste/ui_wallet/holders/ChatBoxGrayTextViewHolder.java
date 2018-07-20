package com.pagatodo.yaganaste.ui_wallet.holders;

import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

public class ChatBoxGrayTextViewHolder extends GenericHolder {

    private StyleTextView textChat;
    private StyleTextView textHora;

    public ChatBoxGrayTextViewHolder(View itemView) {
        super(itemView);
        init();
    }

    @Override
    public void init() {
        this.textChat = this.itemView.findViewById(R.id.text_chat);
        this.textHora = this.itemView.findViewById(R.id.text_hora);
    }

    @Override
    public void bind(Object item, OnClickItemHolderListener listener) {
        ChatBoxTextViewHolder.Chatholder holder = (ChatBoxTextViewHolder.Chatholder) item;
        this.textChat.setText(holder.textChat);
        this.textHora.setText(holder.textHora);
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
