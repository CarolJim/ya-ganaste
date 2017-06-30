package com.pagatodo.yaganaste.interfaces.enums;

import com.pagatodo.yaganaste.interfaces.IEnumSpinner;

/**
 * Created by Jordan on 23/03/2017.
 */

public enum States implements IEnumSpinner {

    S0("0", "Lugar de Nacimiento"),

    S1("1", "AGUASCALIENTES"),

    S2("2", "BAJA CALIFORNIA"),

    S3("3", "BAJA CALIFORNIA SUR"),

    S4("4", "CAMPECHE"),

    S5("5", "CHIHUAHUA"),

    S6("6", "COAHUILA"),

    S7("7", "COLIMA"),

    S8("8", "CHIAPAS"),

    S9("9", "DISTRITO FEDERAL"),

    S10("10", "DURANGO"),

    S11("11", "GUERRERO"),

    S12("12", "GUANAJUATO"),

    S13("13", "HIDALGO"),

    S14("14", "JALISCO"),

    S15("15", "MÉXICO"),

    S16("16", "MICHOACAN"),

    S17("17", "MORELOS"),

    S18("18", "NUEVO LEÓN"),

    S19("19", "NAYARIT"),

    S20("20", "OAXACA"),

    S21("21", "PUEBLA"),

    S22("22", "QUINTANA ROO"),

    S23("23", "QUERETARO"),

    S24("24", "SINALOA"),

    S25("25", "SAN LUIS POTOSI"),

    S26("26", "SONORA"),

    S27("27", "TABASCO"),

    S28("28", "TLAXCALA"),

    S29("29", "TAMAULIPAS"),

    S30("30", "VERACRUZ"),

    S31("31", "YUCATÁN"),

    S32("32", "ZACATECAS"),

    S33("33", "Extranjero");


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

