package com.pagatodo.yaganaste.interfaces.enums;

import com.pagatodo.yaganaste.interfaces.IEnumSpinner;

/**
 * Created by Jordan on 23/03/2017.
 */

public enum Nacionalities implements IEnumSpinner {
    N0("0", "Nacionalidad"),

    N1("1", "Mexicana"),

    N2("2", "Extranjero");

    private String name;
    private String id;

    private Nacionalities(String i, String name) {
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

