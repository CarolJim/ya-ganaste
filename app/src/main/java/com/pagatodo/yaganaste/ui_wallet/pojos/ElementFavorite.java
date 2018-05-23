package com.pagatodo.yaganaste.ui_wallet.pojos;

import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;

public class ElementFavorite implements ElementGlobal {

    private int idOperacion;
    private Favoritos favoritos;

    public ElementFavorite(int idOperacion, Favoritos favoritos) {
        this.idOperacion = idOperacion;
        this.favoritos = favoritos;
    }

    public Favoritos getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(Favoritos favoritos) {
        this.favoritos = favoritos;
    }

    @Override
    public int getIdOperacion() {
        return this.idOperacion;
    }
}
