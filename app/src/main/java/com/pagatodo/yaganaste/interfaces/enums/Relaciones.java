package com.pagatodo.yaganaste.interfaces.enums;

import com.pagatodo.yaganaste.interfaces.IEnumSpinner;
import com.pagatodo.yaganaste.ui.cupo.view.IEnumSpinnerCupo;

/**
 * Created by Tato on 01/08/17.
 */

public enum Relaciones implements IEnumSpinnerCupo {

    R0("0", ""),
    R1("1", "Madre"),
    R2("2", "Padre"),
    R3("3", "Abuelo(a)"),
    R4("4", "Hijo(a)"),
    R5("5", "Hermano(a)"),
    R6("6", "Nieto(a)"),
    R7("7", "Cónyuge"),
    R8("8", "Concubino(a)"),
    R9("9", "Amistad"),
    R10("10", "Primo(a)"),
    R11("11", "Tío(a)"),
    R12("12", "Sobrino(a)"),
    R13("13", "Suegro(a)"),
    R14("14", "Hijastro(a)"),
    R15("15", "Cuñado(a)"),
    R16("16", "Nuera"),
    R17("17", "Yerno"),
    R18("18", "Compadre"),
    R19("19", "Comadre"),
    R20("20", "Padrino"),
    R21("21", "Madrina"),
    R22("22", "Ahijado(a)"),
    R23("23", "Novio(a)"),
    R24("24", "Vecino(a)"),
    R25("25", "Conocido(a)"),
    R26("26", "Empleado(a)"),
    R27("27", "Empresa"),
    R28("28", "Jefe(a)"),
    R29("29", "Obligado Solidario"),
    R30("30", "Proveedor(a)");

    private String name;
    private String id;

    Relaciones(String i, String name) {
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
