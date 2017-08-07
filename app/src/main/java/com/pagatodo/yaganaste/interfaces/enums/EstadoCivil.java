package com.pagatodo.yaganaste.interfaces.enums;

import com.pagatodo.yaganaste.interfaces.IEnumSpinner;

/**
 * Created by Tato on 01/08/17.
 */

public enum EstadoCivil implements IEnumSpinner {

    E0("0", ""),
    E1("1", "Soltero"),
    E2("2", "Casado"),
    E3("3", "Viudo"),
    E4("4", "Otro");

    private String name;
    private String id;

    EstadoCivil(String i, String name) {
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
