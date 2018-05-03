package com.pagatodo.yaganaste.ui_wallet.patterns;

import android.content.Context;
import android.view.ViewGroup;

public abstract class Builder {

    public Context context;
    public ViewGroup parent;

    public Builder(Context context, ViewGroup parent){
        this.context = context;
        this.parent = parent;
    }

    public abstract void createLeaf(Object object);


}
