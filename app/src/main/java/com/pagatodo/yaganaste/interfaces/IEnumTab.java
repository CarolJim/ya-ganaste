package com.pagatodo.yaganaste.interfaces;

import android.content.Context;

/**
 * @author Juan Guerra on 24/03/17
 */

public interface IEnumTab {

    int NO_ICON = -10;
    String NO_NAME = "";

    String getName(Context context);

    int getIconRes();
}
