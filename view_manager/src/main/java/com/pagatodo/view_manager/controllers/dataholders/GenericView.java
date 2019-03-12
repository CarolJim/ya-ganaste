package com.pagatodo.view_manager.controllers.dataholders;

import android.view.View;
import android.view.ViewGroup;

import com.pagatodo.view_manager.controllers.LauncherHolder;
import com.pagatodo.view_manager.controllers.LauncherView;
import com.pagatodo.view_manager.controllers.OnHolderListener;

public abstract class GenericView<T> implements LauncherView {

    private T object;

    public GenericView(T object) {
        this.object = object;
    }

    @Override
    abstract public void init();

    @Override
    abstract public void bind();

    @Override
    abstract public void inflate(ViewGroup layout);

    @Override
    abstract public View getView();

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
