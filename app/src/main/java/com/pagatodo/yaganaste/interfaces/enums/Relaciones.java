package com.pagatodo.yaganaste.interfaces.enums;

import com.pagatodo.yaganaste.interfaces.IEnumSpinner;

/**
 * Created by Tato on 01/08/17.
 */

public enum Relaciones implements IEnumSpinner {

    R0("0", "Realción"),
    R1("1", "Relación 1"),
    R2("2", "Relación 2"),
    R3("3", "Relación 3"),
    R4("4", "Relación 4");

    private String name;
    private String id;

    Relaciones(String i, String name) {
        this.name = name;
        this.id = i;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
