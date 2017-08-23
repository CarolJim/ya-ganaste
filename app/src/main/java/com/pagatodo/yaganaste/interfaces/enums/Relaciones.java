package com.pagatodo.yaganaste.interfaces.enums;

import com.pagatodo.yaganaste.interfaces.IEnumSpinner;
import com.pagatodo.yaganaste.ui.cupo.view.IEnumSpinnerCupo;

/**
 * Created by Horacio on 01/08/17.
 */

public enum Relaciones implements IEnumSpinnerCupo {

    R0("0", ""),
    R1("1", "Madre"),
    R2("2", "Padre"),
    R3("3", "Cónyuge"),
    R4("4", "Amistad"),
    R5("5", "Primo(a)"),
    R6("6", "Tío(a)"),
    R7("7", "Sobrino(a)"),
    R8("8", "Conocido(a)"),
    R9("9", "Proveedor(a)"),
    R10("10", "Hermano(a)"),
    R11("11", "Abuelo(a)"),
    R12("12", "Hijo(a)"),
    R13("13", "Nieto(a)"),
    R14("14", "Concubino(a)"),
    R15("15", "Hijastro(a)"),
    R16("16", "Suegro(a)"),
    R17("17", "Cuñado(a)"),
    R18("18", "Nuera"),
    R19("19", "Yerno"),
    R20("20", "Compadre"),
    R21("21", "Comadre"),
    R22("22", "Padrino"),
    R23("23", "Ahijado(a)"),
    R24("24", "Vecino(a)"),
    R25("25", "Novio(a)"),
    R26("26", "Empleado(a)"),
    R27("27", "Empresa"),
    R28("28", "Jefe(a)"),
    R29("29", "Madrina"),
    R30("30", "Obligado Solidario");

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
