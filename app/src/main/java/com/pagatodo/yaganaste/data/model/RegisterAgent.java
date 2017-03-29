package com.pagatodo.yaganaste.data.model;

import com.pagatodo.yaganaste.data.model.webservice.request.adtvo.CuestionarioEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 27/03/2017.
 */

public class RegisterAgent {

    //Dato de usuario
    private int tipoAgente;
    private String nombre = "";
    private String telefono = "";
    private String razonSocial = "";
    private int Giro;
    private int subGiro;
    private String latitud = "";
    private String longitud = "";
    private List<CuestionarioEntity> cuestionario;
    private static RegisterAgent registerUser;

    private RegisterAgent(){
        cuestionario = new ArrayList<CuestionarioEntity>();
    }

    public static synchronized RegisterAgent getInstance(){

        if(registerUser == null)
            registerUser = new RegisterAgent();

        return registerUser;

    }

    public int getTipoAgente() {
        return tipoAgente;
    }

    public void setTipoAgente(int tipoAgente) {
        this.tipoAgente = tipoAgente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public int getGiro() {
        return Giro;
    }

    public void setGiro(int giro) {
        Giro = giro;
    }

    public int getSubGiro() {
        return subGiro;
    }

    public void setSubGiro(int subGiro) {
        this.subGiro = subGiro;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public List<CuestionarioEntity> getCuestionario() {
        return cuestionario;
    }

    public void setCuestionario(List<CuestionarioEntity> cuestionario) {
        this.cuestionario = cuestionario;
    }

    public static RegisterAgent getRegisterUser() {
        return registerUser;
    }

    public static void setRegisterUser(RegisterAgent registerUser) {
        RegisterAgent.registerUser = registerUser;
    }
}
