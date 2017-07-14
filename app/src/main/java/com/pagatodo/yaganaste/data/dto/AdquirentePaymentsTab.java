package com.pagatodo.yaganaste.data.dto;

import android.content.Context;

import com.pagatodo.yaganaste.interfaces.IEnumTab;

/**
 * @author Juan Guerra on 28/03/2017.
 */

public class AdquirentePaymentsTab implements IEnumTab {

    private String name;
    private String date;

    public AdquirentePaymentsTab(String name, String date) {
        this.name = name;
        this.date = date;
    }

    @Override
    public String getName(Context context) {
        return name;
    }

    @Override
    public int getIconRes() {
        return NO_ICON;
    }

    public String getDate() {
        return this.date;
    }
}