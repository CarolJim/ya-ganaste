package com.pagatodo.yaganaste.data.dto;

/**
 * @author Juan Guerra on 28/03/17.
 */
public class ItemMovements<T> {

    private String premio;
    private String marca;
    private double monto;
    private String date;
    private String month;
    private int color;
    private T movement;

    public ItemMovements(String premio, String marca, double monto, String date, String month, int color) {
        this(premio, marca, monto, date, month, color, null);
    }

    public ItemMovements(String premio, String marca, double monto, String date, String month, int color, T movement) {
        this.premio = premio;
        this.marca = marca;
        this.monto = monto;
        this.date = date;
        this.month = month;
        this.color = color;
        this.movement = movement;
    }

    public String getPremio() {
        return premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public T getMovement() {
        return movement;
    }
}
