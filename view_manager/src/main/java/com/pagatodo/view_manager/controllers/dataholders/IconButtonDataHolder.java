package com.pagatodo.view_manager.controllers.dataholders;

import android.graphics.drawable.Drawable;

public class IconButtonDataHolder {
    private Drawable iconRes;
    private String name;

    public IconButtonDataHolder(Drawable iconRes, String name) {
        this.iconRes = iconRes;
        this.name = name;
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
}
