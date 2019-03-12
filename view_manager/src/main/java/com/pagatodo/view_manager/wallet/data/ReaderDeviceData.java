package com.pagatodo.view_manager.wallet.data;

import android.graphics.drawable.Drawable;

public class ReaderDeviceData{

    private Drawable resImg;

    private ReaderDeviceData(Drawable resImg) {
        this.resImg = resImg;
    }

    public static ReaderDeviceData create(Drawable resImg){
        return new ReaderDeviceData(resImg);
    }

    public Drawable getResImg() {
        return resImg;
    }

    public void setResImg(Drawable resImg) {
        this.resImg = resImg;
    }
}
