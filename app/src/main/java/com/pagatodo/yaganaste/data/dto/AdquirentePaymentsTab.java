package com.pagatodo.yaganaste.data.dto;

import android.content.Context;

import com.pagatodo.yaganaste.interfaces.IEnumTab;

/**
 * @author Juan Guerra on 28/03/2017.
 */

public class AdquirentePaymentsTab implements IEnumTab {

    private String name;
    private String dateStart, dateEnd;

    public AdquirentePaymentsTab(String name, String dateStart, String dateEnd) {
        this.name = name;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    @Override
    public String getName(Context context) {
        return name;
    }

    @Override
    public int getIconRes() {
        return NO_ICON;
    }

    public String getDateStart() {
        return this.dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }
}