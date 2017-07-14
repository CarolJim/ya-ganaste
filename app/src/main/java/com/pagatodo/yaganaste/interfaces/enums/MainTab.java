package com.pagatodo.yaganaste.interfaces.enums;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IEnumTab;

/**
 * @author Juan Guerra on 24/03/17
 */

public enum MainTab implements IEnumTab {

    TAB1(R.string.main_tab_inicio, R.mipmap.icon_tab_ya_white),
    TAB2(R.string.main_tab_pagos, R.drawable.vector_pagos),
    TAB3(R.string.main_tab_depositos, R.drawable.vector_depositos),
    TAB4(R.string.main_tab_cobros, R.drawable.ic_tab_cobros);

    @StringRes
    private int name;

    @DrawableRes
    private int icon;

    MainTab(@StringRes int name, @DrawableRes int icon) {
        this.name = name;
        this.icon = icon;
    }


    @Override
    public String getName(Context context) {
        return context.getString(name);
    }

    @Override
    @DrawableRes
    public int getIconRes() {
        return this.icon;
    }


}
