package com.pagatodo.yaganaste.interfaces.enums;

import com.pagatodo.yaganaste.ui.cupo.view.IEnumSpinnerCupo;

/**
 * Created by Armando Sandoval on 12/09/2017.
 */

public enum ParentescoCUPO implements IEnumSpinnerCupo {
    R0("0", "Parentesco"),
    R1("1", "Padre"),
    R2("2", "Madre"),
    R3("3", "Hermano(a)"),
    R4("4", "Hijo(a)"),
    R5("5", "Hijastro(a)"),
    R6("6", "Cónyuge"),
    R7("7", "Concubino(a)"),
    R8("8", "Suegro(a)"),
    R9("9", "Cuñado(a)"),
    R10("10", "Nuera"),
    R11("11", "Yerno"),
    R12("12", "Abuelo(a)"),
    R13("13", "Nieto(a)"),
    R14("14", "Tío(a)"),
    R15("15", "Primo(a)"),
    R16("16", "Sobrino(a)");

    private String name;
    private String id;

    ParentescoCUPO(String i, String name) {
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
