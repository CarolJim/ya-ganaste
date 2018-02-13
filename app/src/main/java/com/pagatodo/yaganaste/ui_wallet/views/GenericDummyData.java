package com.pagatodo.yaganaste.ui_wallet.views;

/**
 * Created by FranciscoManzo on 12/02/2018.
 * Se encarga de proporcionar datos basicos de estructura simple
 * 1 - Titulo
 * 2 - Descripcion
 * 3 - Imagen
 * 4 - Fecha
 */

public class GenericDummyData {
    String title;
    String desc;
    int logo;
    String fecha;
    int extraInt;

    public GenericDummyData(String title, String desc, int logo, String fecha, int extraInt) {
        this.title = title;
        this.desc = desc;
        this.logo = logo;
        this.fecha = fecha;
        this.extraInt = extraInt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getExtraInt() {
        return extraInt;
    }

    public void setExtraInt(int extraInt) {
        this.extraInt = extraInt;
    }
}
