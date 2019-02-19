package com.pagatodo.view_manager.controllers.dataholders;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class IconButtonDataHolder implements Serializable {
    private Drawable iconRes;
    private String name;
    private String namCom;
    private String imageUrl;
    private TYPE type;
    private Object T;

    public IconButtonDataHolder(Drawable iconRes, String name) {
        this.iconRes = iconRes;
        this.name = name;
    }

    public IconButtonDataHolder(Drawable iconRes, String name, String namCom, TYPE type) {
        this.iconRes = iconRes;
        this.name = name;
        this.namCom = namCom;
        this.type = type;
    }

    public IconButtonDataHolder(String imageUrl, String name, String namCom,  TYPE type) {
        this.name = name;
        this.namCom = namCom;
        this.imageUrl = imageUrl;
        this.type = type;

    }

    public Object getT() {
        return T;
    }

    public void setT(Object t) {
        T = t;
    }

    public Drawable getIconRes() {
        return iconRes;
    }

    public void setIconRes(Drawable iconRes) {
        this.iconRes = iconRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public String getNamCom() {
        return namCom;
    }

    public void setNamCom(String namCom) {
        this.namCom = namCom;
    }

    public enum TYPE{
        ITEM_RECHARGE, ITEM_RECHARGE_FAV, ADD
    }
}
