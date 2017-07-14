package com.pagatodo.yaganaste.data.model;

import java.io.Serializable;

/**
 * Created by jvazquez on 15/05/2017.
 */

public class Cupon implements Serializable {
    private String imagenWeb = "";
    private String descripcionCupon = "";
    private String finVigencia = "";
    private String numeroCupon = "";
    private int idTipo = 0;
    private String imagenMovil = "";

    public Cupon() {

    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getDescripcionCupon() {
        return descripcionCupon;
    }

    public void setDescripcionCupon(String descripcionCupon) {
        this.descripcionCupon = descripcionCupon;
    }

    public String getFinVigencia() {
        return finVigencia;
    }

    public void setFinVigencia(String finVigencia) {
        this.finVigencia = finVigencia;
    }

    public String getImagenMovil() {
        return imagenMovil;
    }

    public void setImagenMovil(String imagenMovil) {
        this.imagenMovil = imagenMovil;
    }

    public String getImagenWeb() {
        return imagenWeb;
    }

    public void setImagenWeb(String imagenWeb) {
        this.imagenWeb = imagenWeb;
    }

    public String getNumeroCupon() {
        return numeroCupon;
    }

    public void setNumeroCupon(String numeroCupon) {
        this.numeroCupon = numeroCupon;
    }
}
