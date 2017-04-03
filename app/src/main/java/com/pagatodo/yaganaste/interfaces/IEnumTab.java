package com.pagatodo.yaganaste.interfaces;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * @author Juan Guerra on 24/03/17
 */

public interface IEnumTab {

    public static final int NO_ICON = -10;

    String getName(Context context);

    int getIconRes();
}
