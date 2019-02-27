package com.pagatodo.view_manager.pages;

public enum Months {
    ENERO("Ene"),
    FEBRERO("Feb"),
    MARZO("Mar"),
    ABRIL("Abr"),
    MAYO("May"),
    JUNIO("Jun"),
    JULIO("Jul"),
    AGOSTO("Ago"),
    SEPTIEMBRE("Sep"),
    OCTUBRE("Oct"),
    NOVIEMBRE("Nov");

    private String name;

    Months(String s) {
        this.name = s;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
