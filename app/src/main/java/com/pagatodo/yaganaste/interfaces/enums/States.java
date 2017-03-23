package com.pagatodo.yaganaste.interfaces.enums;

import com.pagatodo.yaganaste.interfaces.IEnumSpinner;

/**
 * Created by Jordan on 23/03/2017.
 */

public enum States implements IEnumSpinner {
    S1("1", "Aguascalientes"),

    S2("2", "Baja California"),

    S3("3", "Baja California Sur"),

    S4("4", "Campeche"),

    S5("5", "Chiapas"),

    S6("6", "Chihuahua"),

    S7("7", "Coahuila"),

    S8("8", "Colima"),

    S9("9", "Ciudad de México"),

    S10("10", "Durango"),

    S11("11", "Estado de México"),

    S12("12", "Guanajuato"),

    S13("13", "Guerrero"),

    S14("14", "Hidalgo"),

    S15("15", "Jalisco"),

    S16("16", "Michoacán"),

    S17("17", "Morelos"),

    S18("18", "Nayarit"),

    S19("19", "Nuevo León"),

    S20("20", "Oaxaca"),

    S21("21", "Puebla"),

    S22("22", "Querétaro"),

    S23("23", "Quintana Roo"),

    S24("24", "San Luis Potosí"),

    S25("25", "Sinaloa"),

    S26("26", "Sonora"),

    S27("27", "Tabasco"),

    S28("28", "Tamaulipas"),

    S29("29", "Tlaxcala"),

    S30("30", "Veracruz"),

    S31("31", "Yucatán"),

    S32("32", "Zacatecas");

    private String name;
    private String id;

    private States(String i, String name) {
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

