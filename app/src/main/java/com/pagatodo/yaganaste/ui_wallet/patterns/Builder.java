package com.pagatodo.yaganaste.ui_wallet.patterns;

import android.content.Context;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.ui_wallet.holders.LauncherHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;

public abstract class Builder {

    public Context context;
    public ViewGroup parent;

    public Builder(Context context, ViewGroup parent){
        this.context = context;
        this.parent = parent;
    }

    //public abstract void createLeaf(Object object);

    public abstract void addView(LauncherHolder holder, Object object, OnClickItemHolderListener listener);

    public abstract LauncherHolder getholder();
}
