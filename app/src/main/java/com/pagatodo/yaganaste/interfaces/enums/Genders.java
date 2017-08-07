package com.pagatodo.yaganaste.interfaces.enums;

import com.pagatodo.yaganaste.interfaces.IEnumSpinner;

/**
 * Created by Jordan on 23/03/2017.
 */

public enum Genders implements IEnumSpinner {

    G1(1, "H", "Mujer"),
    G2(2, "M", "Hombre");

    private String name;
    private String id;

    Genders(int id, String i, String name) {
        this.name = name;
        this.id = i;
    }

    @Override
    public String toString() {
        return this.name;
    }


    public String getIdentificador() {
        return this.id;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public IEnumSpinner getItemById(int id) {
        for (IEnumSpinner item : values()) {
            if (item.getId() == id) {
                return item;
            }
        }
        return G1;
    }
}
