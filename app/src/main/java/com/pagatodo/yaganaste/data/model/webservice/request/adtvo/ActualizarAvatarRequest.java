package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class ActualizarAvatarRequest implements Serializable {

    private String Imagen = "";
    private String Extension = "";
    //private String ImagenAvatarURL = "";


    public ActualizarAvatarRequest() {
    }

    public ActualizarAvatarRequest(String base64Image, String mExtension) {
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

//    public String getImagenAvatarURL() {
//        return ImagenAvatarURL;
//    }
//
//    public void setImagenAvatarURL(String imagenAvatarURL) {
//        ImagenAvatarURL = imagenAvatarURL;
//    }
}
