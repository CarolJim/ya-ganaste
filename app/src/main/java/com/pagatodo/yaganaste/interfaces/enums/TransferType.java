package com.pagatodo.yaganaste.interfaces.enums;

import android.content.Context;

import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IEnumTab;

/**
 * Created by Jordan on 20/04/2017.
 */

public enum TransferType implements IEnumTab {
    NUMERO_TELEFONO(R.string.transfer_phone_cellphone, 1, R.string.details_telefono),
    NUMERO_TARJETA(R.string.debit_card_number, 2, R.string.details_tarjeta),
    CLABE(R.string.transfer_cable, 3, R.string.details_cable),
    QR_CODE(R.string.transfer_qr, 4, R.string.details_qr);

    private int name;
    private int shortName;
    private int id;

    TransferType(int name, int id, int shortName) {
        this.name = name;
        this.id = id;
        this.shortName = shortName;
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

    public String getShortName() {
        return App.getInstance().getString(shortName);
    }



}
