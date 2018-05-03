package com.pagatodo.yaganaste.ui_wallet.pojos;

import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

/**
 * Created by icruz on 13/02/2018.
 */

public class TextData {

    private int titleLeft;
    private String titleRight;

    public TextData(int titleLeft, String titleRight) {
        this.titleLeft = titleLeft;
        this.titleRight = titleRight;
    }

    public int getTitleLeft() {
        return titleLeft;
    }

    public void setTitleLeft(int titleLeft) {
        this.titleLeft = titleLeft;
    }

    public String getTitleRight() {
        return titleRight;
    }

    public void setTitleRight(String titleRight) {
        this.titleRight = titleRight;
    }

}
