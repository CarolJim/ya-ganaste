package com.pagatodo.yaganaste.interfaces.enums;

import com.pagatodo.yaganaste.interfaces.IEnumSpinner;

/**
 * Created by Jordan on 03/08/2017.
 */

public enum Parentescos implements IEnumSpinner{
    PARENTESCO(0, "Parentesco"),
    MADRE(1, "Madre"),
    PADRE(2, "Padre"),
    CÓNYUGE(3, "Cónyuge"),
    AMISTAD(4, "Amistad"),
    PRIMO(5, "Primo(a)"),
    TÍO(6, "Tío(a)"),
    SOBRINO(7, "Sobrino(a)"),
    CONOCIDO(8, "Conocido(a)"),
    PROVEEDOR(9, "Proveedor(a)"),
    HERMANO(10, "Hermano(a)"),
    ABUELO(11, "Abuelo(a)"),
    HIJO(12, "Hijo(a)"),
    NIETO(13, "Nieto(a)"),
    CONCUBINO(14, "Concubino(a)"),
    HIJASTRO(15, "Hijastro(a)"),
    SUEGRO(16, "Suegro(a)"),
    CUÑADO(17, "Cuñado(a)"),
    NUERA(18, "Nuera"),
    YERNO(19, "Yerno"),
    COMPADRE(20, "Compadre"),
    COMADRE(21, "Comadre"),
    PADRINO(22, "Padrino"),
    AHIJADO(23, "Ahijado(a)"),
    VECINO(24, "Vecino(a)"),
    NOVIO(25, "Novio(a)"),
    EMPLEADO(26, "Empleado(a)"),
    EMPRESA(27, "Empresa"),
    JEFE(28, "Jefe(a)"),
    MADRINA(29, "Madrina");

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

