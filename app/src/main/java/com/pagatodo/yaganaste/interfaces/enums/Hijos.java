package com.pagatodo.yaganaste.interfaces.enums;

import com.pagatodo.yaganaste.interfaces.IEnumSpinner;
import com.pagatodo.yaganaste.ui.cupo.view.IEnumSpinnerCupo;

/**
 * Created by Tato on 01/08/17.
 */

public enum Hijos implements IEnumSpinnerCupo {

    H0("0", ""),
    H1("1", "0"),
    H2("2", "1"),
    H3("3", "2"),
    H4("4", "3"),
    H5("5", "4"),
    H6("6", "5"),
    H7("7", "6"),
    H8("8", "7"),
    H9("9", "8"),
    H10("10", "9"),
    H11("11", "10");

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
