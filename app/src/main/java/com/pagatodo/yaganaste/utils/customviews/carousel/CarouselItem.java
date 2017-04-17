package com.pagatodo.yaganaste.utils.customviews.carousel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.pagatodo.yaganaste.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class CarouselItem extends FrameLayout
        implements Comparable<CarouselItem> {

    public static int DRAG = 0;
    public static int CLICK = 1;
    private ImageView mImage;
    private CircleImageView mImageCiruclar;
    private int drawable;
    private String imageUrl;
    private String color;
    private int index;
    private float currentAngle;
    private float itemX;
    private float itemY;
    private float itemZ;
    private boolean drawn;
    private Context context;
    private boolean visible;
    private boolean empty;
    private Matrix mCIMatrix;
    private int gestureType;
    private int idComercio;

    public CarouselItem(Context context) {
        super(context);
        this.context = context;
        inflateLayout();
    }

    public CarouselItem(Context context, int idComercio) {
        super(context);
        this.context = context;
        this.idComercio = idComercio;
        inflateLayout();
    }

    public CarouselItem(Context context, int resource, int gestureType, int idComercio) {
        super(context);
        this.context = context;
        this.gestureType = gestureType;
        inflateLayout();
        this.drawable = resource;
        this.idComercio = idComercio;
        mImage.setImageDrawable(ContextCompat.getDrawable(context, drawable));

    }

    public CarouselItem(Context context, String imageUrl, int gestureType, int idComercio) {
        super(context);
        this.context = context;
        this.gestureType = gestureType;
        this.imageUrl = imageUrl;
        this.idComercio = idComercio;
        inflateLayout();
        Glide.with(getContext()).load(imageUrl).crossFade(0).into(mImage);

    }

    public CarouselItem(Context context, int resource, String color, int gestureType, int idComercio) {
        super(context);
        this.context = context;
        this.gestureType = gestureType;
        this.drawable = resource;
        this.idComercio = idComercio;
        this.color = color;
        inflateLayout(color);
        mImage.setImageDrawable(ContextCompat.getDrawable(context, drawable));

    }

    public CarouselItem(Context context, String imageUrl, String color, int gestureType, int idComercio) {
        super(context);
        this.context = context;
        this.gestureType = gestureType;
        this.imageUrl = imageUrl;
        this.idComercio = idComercio;
        this.color = color;
        inflateLayout(color);
        Glide.with(context).load(imageUrl).crossFade(0).placeholder(R.mipmap.logo_ya_ganaste).error(R.mipmap.logo_ya_ganaste).into(mImage);

    }


    private void inflateLayout() {
        LayoutParams params =
                new LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);

        this.setLayoutParams(params);
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemTemplate = inflater.inflate(R.layout.item_gallery, this, true);
        mImage = (ImageView) itemTemplate.findViewById(R.id.imgItemGalleryPay);
        mImageCiruclar = (CircleImageView) itemTemplate.findViewById(R.id.imgItemGalleryMark);
//        View itemTemplate = inflater.inflate(R.layout.carrousel_item, this, true);
//        mImage = (ImageView) itemTemplate.findViewById(R.id.statusIv);

    }

    private void inflateLayout(String color) {
        LayoutParams params =
                new LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);

        this.setLayoutParams(params);
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemTemplate = inflater.inflate(R.layout.item_gallery, this, true);
        mImage = (ImageView) itemTemplate.findViewById(R.id.imgItemGalleryPay);
        mImageCiruclar = (CircleImageView) itemTemplate.findViewById(R.id.imgItemGalleryMark);
        mImageCiruclar.setBorderColor(Color.parseColor(color));
//        View itemTemplate = inflater.inflate(R.layout.carrousel_item, this, true);
//        mImage = (ImageView) itemTemplate.findViewById(R.id.statusIv);

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

    public ImageView getmImage() {
        return mImage;
    }

    public void setmImage(ImageView mImage) {
        this.mImage = mImage;
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

    public void setImageBitmap(Bitmap bitmap) {
        mImage.setImageBitmap(bitmap);

    }

    public void setImageDrawable(int drawable) {
        this.drawable = drawable;
        mImage.setImageDrawable(ContextCompat.getDrawable(context, drawable));

    }

    Matrix getCIMatrix() {
        return mCIMatrix;
    }

    void setCIMatrix(Matrix mMatrix) {
        this.mCIMatrix = mMatrix;
    }

    public int getIdComercio(){
        return this.idComercio;
    }

    public String getColor() {
        return this.color;
    }
}