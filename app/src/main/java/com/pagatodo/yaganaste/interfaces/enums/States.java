package com.pagatodo.yaganaste.interfaces.enums;

import com.pagatodo.yaganaste.interfaces.IEnumSpinner;

/**
 * Created by Jordan on 23/03/2017.
 */

public enum States implements IEnumSpinner {

    S0("0", "Lugar de Nacimiento"),

    S1("1", "Aguascalientes"),
    S2("2", "Baja California"),
    S3("3", "Baja California Sur"),
    S4("4", "Campeche"),
    S5("5", "Chihuahua"),
    S6("6", "Coahuila"),
    S7("7", "Colima"),
    S8("8", "Chiapas"),
    S9("9", "Distrito Federal"),
    S10("10", "Durango"),
    S11("11", "Guerrero"),
    S12("12", "Guanajuato"),
    S13("13", "Hidalgo"),
    S14("14", "Jalisco"),
    S15("15", "México"),
    S16("16", "Michoacan"),
    S17("17", "Morelos"),
    S18("18", "Nuevo León"),
    S19("19", "Nayarit"),
    S20("20", "Oaxaca"),
    S21("21", "Puebla"),
    S22("22", "Quintana Roo"),
    S23("23", "Queretaro"),
    S24("24", "Sinaloa"),
    S25("25", "San Luis Potosí"),
    S26("26", "Sonora"),
    S27("27", "Tabasco"),
    S28("28", "Tlaxcala"),
    S29("29", "Tamaulipas"),
    S30("30", "Veracruz"),
    S31("31", "Yucatán"),
    S32("32", "Zacatecas"),
    S33("33", "Extranjero");


    private String name;
    private String id;

    States(String i, String name) {
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

