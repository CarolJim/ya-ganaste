package com.pagatodo.yaganaste.data.model;

import com.pagatodo.yaganaste.data.model.db.Countries;

/**
 * Created by flima on 27/03/2017.
 */

public class RegisterUser {

    private static RegisterUser registerUser;
    //Dato de usuario
    private String email = "";
    private String contrasenia = "";
    private String telefono = "";
    //Datos Personales
    private String genero = "";
    private String nombre = "";
    private String apellidoPaterno = "";
    private String apellidoMaterno = "";
    private String fechaNacimiento = "";
    private String fechaNacimientoToShow = "";
    private String nacionalidad = "";
    private String lugarNacimiento = "";
    private String idEstadoNacimineto = "";
    //Datos Domicilio Actual
    private String calle = "";
    private String numExterior = "";
    private String numInterior = "";
    private String codigoPostal = "";
    private String estadoDomicilio = "";
    private String colonia = "";
    private String idColonia = "";
    private boolean aceptaTerminos;
    private Countries paisNacimiento;

    private RegisterUser() {

    }

    public Countries getPaisNacimiento() {
        return paisNacimiento;
    }

    public void setPaisNacimiento(Countries paisNacimiento) {
        this.paisNacimiento = paisNacimiento;
    }

    public static synchronized RegisterUser getInstance() {

        if (registerUser == null)
            registerUser = new RegisterUser();

        return registerUser;

    }

    public static void resetRegisterUser() {
        registerUser = null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaNacimientoToShow() {
        return fechaNacimientoToShow;
    }

    public void setFechaNacimientoToShow(String fechaNacimientoToShow) {
        this.fechaNacimientoToShow = fechaNacimientoToShow;
    }

    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumExterior() {
        return numExterior;
    }

    public void setNumExterior(String numExterior) {
        this.numExterior = numExterior;
    }

    public String getNumInterior() {
        return numInterior;
    }

    public void setNumInterior(String numInterior) {
        this.numInterior = numInterior;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getEstadoDomicilio() {
        return estadoDomicilio;
    }

    public void setEstadoDomicilio(String estadoDomicilio) {
        this.estadoDomicilio = estadoDomicilio;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public boolean isAceptaTerminos() {
        return aceptaTerminos;
    }

    public void setAceptaTerminos(boolean aceptaTerminos) {
        this.aceptaTerminos = aceptaTerminos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getIdEstadoNacimineto() {
        return idEstadoNacimineto;
    }

    public void setIdEstadoNacimineto(String idEstadoNacimineto) {
        this.idEstadoNacimineto = idEstadoNacimineto;
    }

    public String getIdColonia() {
        return idColonia;
    }

    public void setIdColonia(String idColonia) {
        this.idColonia = idColonia;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
}
