package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class DataDocuments implements Serializable{

    private int TipoDocumento;
    private String ImagenBase64 = "";
    private String Extension = "";


    public DataDocuments() {
    }

    public int getTipoDocumento() {
        return TipoDocumento;
    }

    public void setTipoDocumento(int tipoDocumento) {
        TipoDocumento = tipoDocumento;
    }

    public String getImagenBase64() {
        return ImagenBase64;
    }

    public void setImagenBase64(String imagenBase64) {
        ImagenBase64 = imagenBase64;
    }

    public String getExtension() {
        return Extension;
    }

    public void setExtension(String extension) {
        Extension = extension;
    }
}
