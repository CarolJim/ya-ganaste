package com.pagatodo.yaganaste.utils;

import android.util.DisplayMetrics;

import com.pagatodo.yaganaste.App;

/**
 * @author Juan Guerra on 05/04/2017.
 */

public class DimUtils {

    private DimUtils() {

    }

    public static int convertDpToPixels(int dp) {
        DisplayMetrics displayMetrics = App.getInstance().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int convertDpToPixels(float dp) {
        DisplayMetrics displayMetrics = App.getInstance().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
