package com.pagatodo.yaganaste.data.dto;

/**
 * @author Juan Guerra on 28/03/17.
 */
public class ItemMovements<T> {

    private String tituloDescripcion;
    private String subtituloDetalle;
    private double monto;
    private String date;
    private String month;
    private int color;
    private T movement;


    public ItemMovements(String tituloDescripcion, String subtituloDetalle, double monto, String date, String month, int color) {
        this(tituloDescripcion, subtituloDetalle, monto, date, month, color, null);
    }

    public ItemMovements(String tituloDescripcion, String subtituloDetalle, double monto, String date, String month, int color, T movement) {
        this.tituloDescripcion = tituloDescripcion;
        this.subtituloDetalle = subtituloDetalle;
        this.monto = monto;
        this.date = date;
        this.month = month;
        this.color = color;
        this.movement = movement;
    }

    public String getTituloDescripcion() {
        return tituloDescripcion;
    }

    public void setTituloDescripcion(String tituloDescripcion) {
        this.tituloDescripcion = tituloDescripcion;
    }

    public String getSubtituloDetalle() {
        return subtituloDetalle;
    }

    public void setSubtituloDetalle(String subtituloDetalle) {
        this.subtituloDetalle = subtituloDetalle;
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

    public void setMovement(T movement) {
        this.movement = movement;
    }
}
