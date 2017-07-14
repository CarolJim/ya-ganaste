package com.pagatodo.yaganaste.data.model;

/**
 * Created by jmhernandez on 11/05/2017.
 */

public class DemoMov {
    private int color;
    private String dia;
    private String mes;
    private String concepto;
    private String descripcion;
    private String monto;
    private String centavos;

    public DemoMov() {

    }

    public DemoMov(int color, String dia, String mes, String concepto, String descripcion, String monto, String centavos) {
        this.color = color;
        this.dia = dia;
        this.mes = mes;
        this.concepto = concepto;
        this.descripcion = descripcion;
        this.monto = monto;
        this.centavos = centavos;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getCentavos() {
        return centavos;
    }

    public void setCentavos(String centavos) {
        this.centavos = centavos;
    }
}
