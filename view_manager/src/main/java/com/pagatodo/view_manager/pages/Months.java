package com.pagatodo.view_manager.pages;

public enum Months {
    ENERO("Ene", 1),
    FEBRERO("Feb",2),
    MARZO("Mar",3),
    ABRIL("Abr",4),
    MAYO("May",5),
    JUNIO("Jun",6),
    JULIO("Jul",7),
    AGOSTO("Ago",8),
    SEPTIEMBRE("Sep",9),
    OCTUBRE("Oct",10),
    NOVIEMBRE("Nov",11),
    DICIEMBRE("dIC",12);

    private String name;
    private int month;

    Months(String s, int month) {
        this.name = s;
        this.month = month;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
