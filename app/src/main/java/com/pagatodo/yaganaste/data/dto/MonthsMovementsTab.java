package com.pagatodo.yaganaste.data.dto;

import android.content.Context;

import com.pagatodo.yaganaste.interfaces.IEnumTab;

/**
 * @author Juan Guerra on 27/03/2017.
 */

public class MonthsMovementsTab implements IEnumTab {

    private String name;

    private int month;
    private int year;

    public MonthsMovementsTab(String name, int month, int year) {
        this.name = name;
        this.month = month;
        this.year = year;
    }

    @Override
    public String getName(Context context) {
        return name;
    }

    @Override
    public int getIconRes() {
        return NO_ICON;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
