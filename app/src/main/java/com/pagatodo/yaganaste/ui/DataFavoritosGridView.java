package com.pagatodo.yaganaste.ui;

/**
 * Created by FranciscoManzo on 27/12/2017.
 * Se encarga de recibir unicamente un texto a mostrar y el icono, para nuestros favoritos
 */

public class DataFavoritosGridView {
    String mName;
    int mDrawable;
    public DataFavoritosGridView(String mName, int mDrawable) {
        this.mName = mName;
        this.mDrawable = mDrawable;
    }

    public String getmName() {
        return mName;
    }

    public int getmDrawable() {
        return mDrawable;
    }
}
