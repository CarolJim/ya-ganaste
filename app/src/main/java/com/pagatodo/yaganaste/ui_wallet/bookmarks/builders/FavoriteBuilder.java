package com.pagatodo.yaganaste.ui_wallet.bookmarks.builders;

import android.content.Context;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.ui_wallet.holders.LauncherHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.ui_wallet.patterns.Builder;

public class FavoriteBuilder extends Builder{

    private LauncherHolder holder;

    public FavoriteBuilder(Context context, ViewGroup parent) {
        super(context, parent);
    }

    @Override
    public void addView(LauncherHolder holder, Object object, OnClickItemHolderListener listener) {
        this.holder = holder;
        this.holder.bind(object, null);
        this.holder.inflate(this.parent);
    }

    @Override
    public LauncherHolder getholder() {
        return this.holder;
    }
}
