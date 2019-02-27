package com.pagatodo.view_manager.controllers.dataholders;

import android.graphics.drawable.Drawable;

public class MovementDataHolder {

    private String day;
    private String month;
    private String movement;
    private String referencia;
    private String amount;
    private Drawable resImage;
    private Object object;

    MovementDataHolder(String day, String month, String movement, String referencia, String amount, Drawable resImage, Object object) {
        this.day = day;
        this.month = month;
        this.movement = movement;
        this.referencia = referencia;
        this.amount = amount;
        this.resImage = resImage;
        this.object = object;
    }

    public static MovementDataHolder create(String day, String month, String movement, String referencia, String amount, Drawable resImage, Object object){
        return new MovementDataHolder(day,month,movement,referencia,amount,resImage,object);
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMovement() {
        return movement;
    }

    public void setMovement(String movement) {
        this.movement = movement;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Drawable getResImage() {
        return resImage;
    }

    public void setResImage(Drawable resImage) {
        this.resImage = resImage;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
