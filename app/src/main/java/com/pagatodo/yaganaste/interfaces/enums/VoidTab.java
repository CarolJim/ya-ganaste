package com.pagatodo.yaganaste.interfaces.enums;

import android.content.Context;

import com.pagatodo.yaganaste.interfaces.IEnumTab;

/**
 * @author Juan Guerra on 24/03/17
 */

public enum VoidTab implements IEnumTab {

    VOID;

    @Override
    public String getName(Context context) {
        return NO_NAME;
    }

    @Override
    public int getIconRes() {
        return NO_ICON;
    }


}
