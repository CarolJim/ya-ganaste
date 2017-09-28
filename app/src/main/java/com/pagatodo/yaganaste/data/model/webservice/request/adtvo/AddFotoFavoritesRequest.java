package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by Francisco Manzo on 21/03/2017.
 */

public class AddFotoFavoritesRequest implements Serializable {

    private String Imagen = "";
    private String Extension = "";

    public AddFotoFavoritesRequest() {
    }

    public AddFotoFavoritesRequest(String base64Image, String mExtension) {
        this.Imagen = base64Image;
        this.Extension = mExtension;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public String getExtension() {
        return Extension;
    }

    public void setExtension(String extension) {
        Extension = extension;
    }
}
