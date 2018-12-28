package com.pagatodo.yaganaste.interfaces.enums;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IEnumTab;

public enum MainTab implements IEnumTab {


    TAB1(R.string.main_tab_inicio, R.drawable.ic_wallet_tab),
    TAB2(R.string.main_tab_send, R.drawable.ic_send_tab),
    TAB3(R.string.main_tab_pagos, R.drawable.ic_pay_tab),
    TAB4(R.string.main_tab_qr, R.drawable.ic_qr_tab);

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
