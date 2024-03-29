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
    TAB3(R.string.child_tab_mony_send, 3, "Envío de Dinero");

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
        return  getMovementById(id);
    }

    public String getDescription() {
        return description;
    }
}
