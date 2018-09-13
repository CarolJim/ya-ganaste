package com.pagatodo.yaganaste.utils.customviews.carousel;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.pagatodo.yaganaste.data.room_db.entities.Comercio;
import com.pagatodo.yaganaste.data.room_db.entities.Favoritos;

import java.io.Serializable;


public class CarouselItem implements Comparable<CarouselItem>, Serializable {

    public static int DRAG = 0;
    public static int CLICK = 1;
    private int drawable;
    private String imageUrl;
    private String color;
    private int index;
    private float currentAngle;
    private float itemX;
    private float itemY;
    private float itemZ;
    private boolean drawn;
    private boolean visible;
    private boolean empty;
    private int gestureType;
    private Comercio comercio;
    private Favoritos favoritos;

    public CarouselItem(Comercio comercio) {
        this.comercio = comercio;
    }

    public CarouselItem(Favoritos favoritos) {
        this.favoritos = favoritos;
    }

    public CarouselItem(int resource, int gestureType, Comercio comercio) {
        this.gestureType = gestureType;
        this.drawable = resource;
        this.comercio = comercio;
    }

    public CarouselItem(int resource, int gestureType, Favoritos favoritos) {
        this.gestureType = gestureType;
        this.drawable = resource;
        this.favoritos = favoritos;

    }

    public CarouselItem(String imageUrl, int gestureType, Comercio comercio) {
        this.gestureType = gestureType;
        this.imageUrl = imageUrl;
        this.comercio = comercio;
    }

    public CarouselItem(String imageUrl, int gestureType, Favoritos favoritos) {
        this.gestureType = gestureType;
        this.imageUrl = imageUrl;
        this.favoritos = favoritos;
    }

    //
    public CarouselItem(int resource, String color, int gestureType, Comercio comercio, Favoritos favoritos) {
        this.gestureType = gestureType;
        this.drawable = resource;
        this.comercio = comercio;
        this.color = color;
    }
//

    /**
     * Metodo usado solamente para crear item de nuevo favorito
     *
     * @param resource
     * @param color
     * @param gestureType
     * @param comercio
     */
    public CarouselItem(int resource, String color, int gestureType, Comercio comercio) {
        this.gestureType = gestureType;
        this.drawable = resource;
        this.comercio = comercio;
        this.color = color;
    }

    //
    public CarouselItem(String imageUrl, String color, int gestureType, Comercio comercio) {
        this.gestureType = gestureType;
        this.imageUrl = imageUrl;
        this.comercio = comercio;
        this.color = color;
        //Glide.with(context).load(imageUrl).crossFade(0).placeholder(R.mipmap.logo_ya_ganaste).error(R.mipmap.logo_ya_ganaste).into(mImage);
    }

    //*
    public CarouselItem(String imageUrl, String color, int gestureType, Favoritos favoritos) {
        this.gestureType = gestureType;
        this.imageUrl = imageUrl;
        this.favoritos = favoritos;
        this.color = color;
    }


    public int getGestureType() {
        return gestureType;
    }

    public void setGestureType(int gestureType) {
        this.gestureType = gestureType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public float getCurrentAngle() {
        return currentAngle;
    }

    public void setCurrentAngle(float currentAngle) {

        if (index == 0 && currentAngle > 5) {
            Log.d("", "");
        }

        this.currentAngle = currentAngle;
    }

    public int compareTo(CarouselItem another) {
        return (int) (another.itemZ - this.itemZ);
    }

    public float getItemX() {
        return itemX;
    }

    public void setItemX(float x) {
        this.itemX = x;
    }

    public float getItemY() {
        return itemY;
    }

    public void setItemY(float y) {
        this.itemY = y;
    }

    public float getItemZ() {
        return itemZ;
    }

    public void setItemZ(float z) {
        this.itemZ = z;
    }

    public boolean isDrawn() {
        return drawn;
    }

    public void setDrawn(boolean drawn) {
        this.drawn = drawn;
    }

    public Comercio getComercio() {
        return this.comercio;
    }

    public Favoritos getFavoritos() {
        return this.favoritos;
    }

    public String getColor() {
        return this.color;
    }
}
