package com.pagatodo.yaganaste.utils;

import android.content.Context;

/**
 * Created by jvazquez on 16/02/2017.
 */

public class ImageLoader {

    //Loaders


    private Context context;

    private ImageLoader(){
    }

    public ImageLoader with(Context context){
        this.context = context;
        return new ImageLoader();
    }

    public enum Loaders {PICASSO, FRESCO, GLIDE, ION}
}
