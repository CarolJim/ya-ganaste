package com.pagatodo.yaganaste.ui_wallet.bookmarks.builders;

import android.view.ViewGroup;

public interface Component {

    void add(Component component);

    void setContent(int id);

    void inflate(ViewGroup layout);

}
