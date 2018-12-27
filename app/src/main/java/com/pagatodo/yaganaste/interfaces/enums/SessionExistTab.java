package com.pagatodo.yaganaste.interfaces.enums;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IEnumTab;

/**
 * @author Juan Guerra on 24/03/17
 */

public enum SessionExistTab implements IEnumTab {

    TAB1(R.string.main_tab_inicio, R.drawable.icon_tab_ya),
    TAB2(R.string.main_tab_pagos, R.drawable.icon_tab_pagos_white),
    TAB3(R.string.main_tab_send, R.drawable.icon_tab_deposito_white);

    @StringRes
    private int name;

    @DrawableRes
    private int icon;

    SessionExistTab(@StringRes int name, @DrawableRes int icon) {
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
