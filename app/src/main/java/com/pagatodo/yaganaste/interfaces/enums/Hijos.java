package com.pagatodo.yaganaste.interfaces.enums;

import com.pagatodo.yaganaste.interfaces.IEnumSpinner;

/**
 * Created by Tato on 01/08/17.
 */

public enum Hijos implements IEnumSpinner {

    H0("0", ""),
    H1("1", "0"),
    H2("2", "1"),
    H3("3", "2"),
    H4("4", "3"),
    H5("5", "4"),
    H6("6", "Mas");

    private String name;
    private String id;

    Hijos(String i, String name) {
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
