package com.pagatodo.yaganaste.ui_wallet.views;

/**
 * Created by Armando Sandoval on 09/01/2018.
 */

public class Color {

    private String name;
    private String hex;


    public Color(String name, String hex) {
        this.hex = hex;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }
}
