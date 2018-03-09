package com.pagatodo.yaganaste.ui_wallet.pojos;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

/**
 * Created by icruz on 14/02/2018.
 */

public class InputText {

    private int hintTextResources;
    private String contentText;
    private String tipo;

    public InputText(int hintText) {
        this.hintTextResources = hintText;
        this.contentText = "";
    }

    public InputText(int hintText, String isPassqord) {
        this.hintTextResources = hintText;
        this.contentText = "";
        this.tipo = isPassqord;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public int getHintText() {
        return hintTextResources;
    }

    public void setHintText(int hintText) {
        this.hintTextResources = hintText;
    }

    public static class ViewHolderInputText {
        public EditText editText;
        public TextInputLayout inputLayout;


    }
}
