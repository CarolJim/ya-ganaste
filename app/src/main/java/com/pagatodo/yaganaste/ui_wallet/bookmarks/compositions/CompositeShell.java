package com.pagatodo.yaganaste.ui_wallet.bookmarks.compositions;

import android.view.ViewGroup;

import com.pagatodo.yaganaste.ui_wallet.bookmarks.builders.Component;

import java.util.ArrayList;
import java.util.List;

public class CompositeShell implements Component {

    private List<Component> components = new ArrayList<>();


    @Override
    public void add(Component component) {
        this.components.add(component);
    }

    @Override
    public void setContent(int id) {

    }

    @Override
    public void inflate(ViewGroup layout) {
        for (Component c : this.components){
            c.inflate(layout);
        }
    }
}
