package com.pagatodo.yaganaste.interfaces.enums;

import com.pagatodo.yaganaste.interfaces.IEnumSpinner;
import com.pagatodo.yaganaste.ui.cupo.view.IEnumSpinnerCupo;

/**
 * Created by Tato on 01/08/17.
 */

public enum EstadoCivil implements IEnumSpinnerCupo {

    E0("0", "No Indicado"),
    E1("1", "Soltero(a)"),
    E2("2", "Casado(a)"),
    E3("3", "Divorciado(a)"),
    E4("4", "Viudo(a)"),
    E5("5", "Unión Libre");

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
