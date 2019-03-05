package com.pagatodo.yaganaste.interfaces;

import android.content.Context;


public interface IEnumTab {

    int NO_ICON = -10;
    String NO_NAME = "";

    String getName(Context context);

    int getIconRes();
}
