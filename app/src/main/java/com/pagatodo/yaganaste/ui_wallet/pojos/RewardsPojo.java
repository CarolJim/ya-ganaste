package com.pagatodo.yaganaste.ui_wallet.pojos;

/**
 * Created by asandovals on 25/04/2018.
 */

public class RewardsPojo {
    String descripcion="";
    int idbeneficio=0;

    public RewardsPojo(String descripcion) {
        this.descripcion = descripcion;
        this.idbeneficio = idbeneficio;
    }
    public RewardsPojo(String descripcion, int idbeneficio) {
        this.descripcion = descripcion;
        this.idbeneficio = idbeneficio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdbeneficio() {
        return idbeneficio;
    }

    public void setIdbeneficio(int idbeneficio) {
        this.idbeneficio = idbeneficio;
    }
}
