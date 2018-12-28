package com.pagatodo.yaganaste.interfaces.enums;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IEnumTab;

public enum MainTabOperador implements IEnumTab {

    TAB2(R.string.main_tab_inicio, R.drawable.ic_wallet_tab),
    TAB3(R.string.main_tab_pagos, R.drawable.ic_pay_tab);
    //TAB4(R.string.main_tab_cobros, R.drawable.ic_tab_cobros);
    //TAB5(R.string.main_tab_movimientos,R.drawable)

    @StringRes
    private int name;

    @DrawableRes
    private int icon;

    MainTabOperador(@StringRes int name, @DrawableRes int icon) {
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
