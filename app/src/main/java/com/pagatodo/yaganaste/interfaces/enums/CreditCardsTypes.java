package com.pagatodo.yaganaste.interfaces.enums;

import android.support.annotation.DrawableRes;

import com.pagatodo.yaganaste.R;

/**
 * Created by Jordan on 24/08/2017.
 */

public enum CreditCardsTypes {
    VISA(1, "Visa", R.drawable.visa),
    MASTER_CARD(2, "Master Card", R.drawable.mastercard_canvas);

    private int id;
    private String description;
    private int image;

    CreditCardsTypes(int id, String description, @DrawableRes int image) {
        this.id = id;
        this.description = description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
