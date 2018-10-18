package com.pagatodo.yaganaste.interfaces.enums;

import com.pagatodo.yaganaste.interfaces.IEnumSpinner;

/**
 * Created by Jordan on 03/08/2017.
 */

public enum Parentescos implements IEnumSpinner {
    PARENTESCO(0, "Parentesco"),
    ABUELO(12, "Abuelo(a)"),
    CONCUBINO(7, "Concubino(a)"),
    CUNIADO(9, "Cuñado(a)"),
    CONYUGE(6, "Cónyuge"),
    HERMANO(3, "Hermano(a)"),
    HIJASTRO(5, "Hijastro(a)"),
    HIJO(4, "Hijo(a)"),
    MADRE(2, "Madre"),
    NIETO(13, "Nieto(a)"),
    NUERA(10, "Nuera"),
    PADRE(1, "Padre"),
    PRIMO(15, "Primo(a)"),
    SOBRINO(16, "Sobrino(a)"),
    SUEGRO(8, "Suegro(a)"),
    TIO(14, "Tío(a)"),
    YERNO(11, "Yerno");

    private String name;
    private int id;

    Parentescos(int id, String descripcion) {
        this.id = id;
        this.name = descripcion;
    }

    @Override
    public IEnumSpinner getItemById(int id) {
        for (IEnumSpinner item : values()) {
            if (item.getName() == name) {
                return item;
            }
        }
        return PARENTESCO;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}

