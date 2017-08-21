package com.pagatodo.yaganaste.interfaces.enums;

import com.pagatodo.yaganaste.interfaces.IEnumSpinner;

/**
 * Created by Jordan on 03/08/2017.
 */

public enum Parentescos implements IEnumSpinner{
    PARENTESCO(0, "Parentesco"),

    PADRE(1	, "Padre"),
    MADRE(2	, "Madre"),
    HERMANO(3	, "Hermano(a)"),
    HIJO(4	, "Hijo(a)"),
    HIJASTRO(5	, "Hijastro(a)"),
    CONYUGE(6	, "Cónyuge"),
    CONCUBINO(7	, "Concubino(a)"),
    SUEGRO(8	, "Suegro(a)"),
    CUNIADO(9	, "Cuñado(a)"),
    NUERA(10, "Nuera"),
    YERNO(11, "Yerno"),
    ABUELO(12, "Abuelo(a)"),
    NIETO(13, "Nieto(a)"),
    TIO(14, "Tío(a)"),
    PRIMO(15, "Primo(a)"),
    SOBRINO(16, "Sobrino(a)");

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
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}

