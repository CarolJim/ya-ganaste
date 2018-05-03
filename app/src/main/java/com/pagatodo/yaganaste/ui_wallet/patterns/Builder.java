package com.pagatodo.yaganaste.ui_wallet.patterns;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.holders.TextDataViewHolder;
import com.pagatodo.yaganaste.ui_wallet.patterns.interfaces.Component;
import com.pagatodo.yaganaste.ui_wallet.patterns.leafs.TextLeaf;
import com.pagatodo.yaganaste.ui_wallet.pojos.TextData;

public abstract class Builder {

    public Context context;
    public ViewGroup parent;

    public Builder(Context context, ViewGroup parent){
        this.context = context;
        this.parent = parent;
    }

    public abstract void createLeaf(Object object);


}
