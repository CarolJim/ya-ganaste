package com.pagatodo.yaganaste.ui_wallet.views;

/**
 * Created by FranciscoManzo on 27/12/2017.
 * Se encarga de recibir unicamente un texto a mostrar y el icono, para nuestros favoritos
 *
 * Para comercios el circulo contorno va con el color de marca, el relleno el negro y el logro
 * del servicio
 *
 * Para favoritos:
 * 1 - Si tiene imagen el contorno con el servicio que llega de color, y la foto alc entro
 * Si no tiene imagen, el contorno del color del servicio (Marca) y las letras en blanco, con relleno negro
 * Hacer el calculo
 */

public class DataFavoritosGridView {
    String mColor;
    String mName;
    String mUrlLogo;

    public DataFavoritosGridView(String mColor, String mName, String mUrlLogo) {
        this.mColor = mColor;
        this.mName = mName;
        this.mUrlLogo = mUrlLogo;
    }

    public String getUrlLogo() {
        return mUrlLogo;
    }

    public String getName() {
        return mName;
    }

    public String getmColor() {
        return mColor;
    }
}
