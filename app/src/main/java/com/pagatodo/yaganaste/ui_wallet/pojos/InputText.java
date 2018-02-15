package com.pagatodo.yaganaste.ui_wallet.pojos;

/**
 * Created by icruz on 14/02/2018.
 */

public class InputText {

    private String hintText;
    private String contentText;
    private boolean isPassqord;

    public InputText(String hintText) {

        this.hintText = hintText;
        this.contentText = "";

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


}
