package com.pagatodo.yaganaste.ui_wallet.pojos;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

/**
 * Created by icruz on 14/02/2018.
 */

public class InputText {

    private String hintText;
    private String contentText;
    private String tipo;


    public InputText(String hintText) {

        this.hintText = hintText;
        this.contentText = "";
    }

    public InputText(String hintText, String isPassqord) {
        this.hintText = hintText;
        this.contentText = "";
        this.tipo=isPassqord;
    }
/*Saber si el textlayout necesita se tipopassword
*
 *
  * */

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

    public String getHintText() {
        return hintText;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
    }

    public static class ViewHolderInputText {
        public EditText editText;
        public TextInputLayout inputLayout;


    }
}
