package com.pagatodo.yaganaste.interfaces.enums;

import com.pagatodo.yaganaste.interfaces.IEnumSpinner;

/**
 * Created by Jordan on 23/03/2017.
 */

public enum Genders implements IEnumSpinner {

    G1("H", "Mujer"),
    G2("M", "Hombre");

    private String name;
    private String id;

    private Genders(String i, String name) {
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
