package com.pagatodo.yaganaste.interfaces.enums;

import com.pagatodo.yaganaste.interfaces.IEnumSpinner;

/**
 * Created by Armando Sandoval on 09/02/2018.
 */

public enum Genero implements IEnumSpinner {
    S0(0, "Genero"),
    S1(1, "Masculino"),
    S2(2, "Femenino");

    private String name;
    private int id;



    Genero(int id, String name) {
        this.name = name;
        this.id = id;
    }
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int getId() {
        return this.id;
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
        return S0;
    }

    public static IEnumSpinner getItemByName(String name){
        for (IEnumSpinner item : values()) {
            if (item.getName() == name) {
                return item;
            }
        }
        return S0;
    }
}
