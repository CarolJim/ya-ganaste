package com.pagatodo.yaganaste.interfaces.enums;

import android.content.Context;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IEnumTab;

/**
 * Created by Jordan on 20/04/2017.
 */

public enum TransferType implements IEnumTab {
    NUMERO_TELEFONO(R.string.transfer_phone_cellphone, 1),
    NUMERO_TARJETA(R.string.debit_card_number, 2),
    CABLE(R.string.transfer_cable, 3);

    private int name;
    private int id;

    TransferType(int name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getName(Context context) {
        return context.getString(this.name);
    }

    public int getId() {
        return this.id;
    }

    @Override
    public int getIconRes() {
        return NO_ICON;
    }

}
