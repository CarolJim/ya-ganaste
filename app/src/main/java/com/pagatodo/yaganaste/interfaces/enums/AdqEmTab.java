package com.pagatodo.yaganaste.interfaces.enums;

import android.content.Context;
import androidx.annotation.StringRes;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IEnumTab;

/**
 * @author Juan Guerra on 27/03/17
 */

public enum AdqEmTab implements IEnumTab {

    EMISOR(R.string.cuenta_personal),
    ADQUIRENTE(R.string.cobros_con_tarjeta);

    @StringRes
    private int name;

    AdqEmTab(@StringRes int name) {
        this.name = name;
    }

    @Override
    public String getName(Context context) {
        return context.getString(name);
    }

    @Override
    public int getIconRes() {
        return NO_ICON;
    }


}
