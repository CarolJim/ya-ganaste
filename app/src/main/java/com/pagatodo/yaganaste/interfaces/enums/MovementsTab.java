package com.pagatodo.yaganaste.interfaces.enums;

import android.content.Context;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.IEnumTab;

/**
 * Created by Jordan on 07/04/2017.
 */

public enum MovementsTab implements IEnumTab {
    TAB1(R.string.child_tab_recharge, 1, "Recarga TAE"),
    TAB2(R.string.child_tab_services, 2, "Pago de Servicio"),
    TAB3(R.string.child_tab_mony_send, 3, "Envío de Dinero"),
    TAB4(0, 4, "Envío Otro Banco"),
    TAB5(0, 5, "Recepción de Dinero"),
    TAB6(0, 6, "Recepción Otro Banco"),;

    private int name;
    private int id;
    private String description;

    MovementsTab(int name, int id, String description) {
        this.name = name;
        this.id = id;
        this.description = description;
    }

    public static MovementsTab getMovementById(int type) {
        for (MovementsTab movementsTab : values()) {
            if (movementsTab.getId() == type) {
                return movementsTab;
            }
        }
        return TAB1;
    }

    @Override
    public String getName(Context context) {
        return context.getString(name);
    }

    public int getId() {
        return id;
    }

    @Override
    public int getIconRes() {
        return 0;
    }

    public MovementsTab getTABfromId(int id) {
        switch (id) {
            case 1:
                return TAB1;
            case 2:
                return TAB2;
            case 3:
                return TAB3;
            default:
                return TAB1;
        }
    }

    public String getDescription() {
        return description;
    }
}
