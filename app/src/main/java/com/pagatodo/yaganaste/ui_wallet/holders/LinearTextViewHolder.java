package com.pagatodo.yaganaste.ui_wallet.holders;

import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;

import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

public class LinearTextViewHolder extends GenericHolder {

    private StyleTextView title;

    public LinearTextViewHolder(View itemView) {
        super(itemView);
        init();
    }

    @Override
    public void init() {
       this.title = this.itemView.findViewById(R.id.title);

    }

    @Override
    public void bind(Object item, OnClickItemHolderListener listener) {
        Videotutorial videotutorial = (Videotutorial) item;
        this.title.setText(videotutorial.getTitle());
        this.itemView.setOnClickListener(view -> listener.onClick(item));

    }

    @Override
    public void inflate(ViewGroup layout) {
        layout.addView(this.itemView);
    }

    @Override
    public View getView() {
        return this.itemView;
    }

    public static class Videotutorial{
        String title;
        String url;

        public Videotutorial(String title, String url) {
            this.title = title;
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
