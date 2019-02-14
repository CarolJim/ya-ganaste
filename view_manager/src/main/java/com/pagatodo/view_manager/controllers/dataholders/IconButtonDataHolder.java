package com.pagatodo.view_manager.controllers.dataholders;

import android.graphics.drawable.Drawable;

public class IconButtonDataHolder {
    private Drawable iconRes;
    private String name;
    private String imageUrl;
    private TYPE type;


    public IconButtonDataHolder(Drawable iconRes, String name) {
        this.iconRes = iconRes;
        this.name = name;
    }

    public IconButtonDataHolder(Drawable iconRes, String name,TYPE type) {
        this.iconRes = iconRes;
        this.name = name;
        this.type = type;
    }

    public IconButtonDataHolder(String imageUrl, String name, TYPE type) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.type = type;
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

    public enum TYPE{
        ITEM_RECHARGE,ITEM_PAY
    }
}
