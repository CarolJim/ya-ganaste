package com.pagatodo.yaganaste.utils;

import android.content.Intent;
import android.support.annotation.ColorRes;
import android.util.SparseIntArray;

import com.pagatodo.yaganaste.R;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Juan Guerra on 28/03/2017.
 */

public class MovementColorsFactory {

    private static final SparseIntArray colors;

    static{
        colors = new SparseIntArray();
        //1=rojo
        //2=verde
        //3=azul
        //4=morado
        colors.put(1, R.color.redcolor);
        colors.put(2, R.color.greencolor);
        colors.put(3, R.color.purpura);
        colors.put(4, 0);
    }
    @ColorRes
    public static int getColorMovement(int typeMovement) {
        return colors.get(typeMovement, R.color.colorAccent);
    }
}
