package com.pagatodo.yaganaste.data.model;

import java.util.ArrayList;

public class RegisterAggregatorNew {
    private static RegisterAggregatorNew registerAggregatorNew;

    /*Datos de Negocio*/
    private String razonSocial = "";
    private boolean aceptaTerminos;
    //Datos de Negocio
    private Integer IdGiro = 0;
    private String Giro = "";
    private String nombreNegocio = "";
    private Giros giroComercio;
    //Datos de QRs
    ArrayList<QRs> qRs;

    public static synchronized RegisterAggregatorNew getInstance(){
        if (registerAggregatorNew==null){
            registerAggregatorNew = new RegisterAggregatorNew();
        }
        return registerAggregatorNew;
    }

    public static RegisterAggregatorNew getRegisterAggregatorNew() {
        return registerAggregatorNew;
    }

    public static void setRegisterAggregatorNew(RegisterAggregatorNew registerAggregatorNew) {
        RegisterAggregatorNew.registerAggregatorNew = registerAggregatorNew;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public boolean isAceptaTerminos() {
        return aceptaTerminos;
    }

    public void setAceptaTerminos(boolean aceptaTerminos) {
        this.aceptaTerminos = aceptaTerminos;
    }

    public Integer getIdGiro() {
        return IdGiro;
    }

    public void setIdGiro(Integer idGiro) {
        IdGiro = idGiro;
    }

    public String getGiro() {
        return Giro;
    }

    public void setGiro(String giro) {
        Giro = giro;
    }

    public String getNombreNegocio() {
        return nombreNegocio;
    }

    public void setNombreNegocio(String nombreNegocio) {
        this.nombreNegocio = nombreNegocio;
    }

    public Giros getGiroComercio() {
        return giroComercio;
    }

    public void setGiroComercio(Giros giroComercio) {
        this.giroComercio = giroComercio;
    }

    public ArrayList<QRs> getqRs() {
        return qRs;
    }

    public void setqRs(ArrayList<QRs> qRs) {
        this.qRs = qRs;
    }
}
