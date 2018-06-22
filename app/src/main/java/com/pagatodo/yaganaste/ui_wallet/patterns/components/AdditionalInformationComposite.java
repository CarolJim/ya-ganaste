package com.pagatodo.yaganaste.ui_wallet.patterns.components;

import android.view.ViewGroup;

import com.pagatodo.yaganaste.ui_wallet.patterns.interfaces.Component;

import java.util.ArrayList;
import java.util.List;

public class AdditionalInformationComposite implements Component {

    private List<Component> components = new ArrayList<>();

    @Override
    public void add(Component component) {
        components.add(component);
    }

    @Override
    public void setContent(Object item) {

    }

    @Override
    public void inflate(ViewGroup layout) {
        for (Component c : components){
            c.inflate(layout);
        }
    }
}
