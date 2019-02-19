package com.pagatodo.yaganaste.data.model;

import com.pagatodo.yaganaste.data.room_db.entities.Paises;

import java.util.ArrayList;

public class RegisterUserNew {

    private static RegisterUserNew registerUserNew;

    public int statusRegistro = 0;
    /* Datos de correo*/
    private String email = "";
    private String contrasenia = "";
    /* Datos Personales*/
    private String genero = "";
    private String nombre = "";
    private String apellidoPaterno = "";
    private String apellidoMaterno = "";
    private String fechaNacimiento = "";
    private String fechaNacimientoToShow = "";
    private String lugarNacimiento = "";
    private String idEstadoNacimineto = "";
    private Paises paisNacimiento;
    private String CURP = "";
    /* Datos Domicilio Actual*/
    private String calle = "";
    private String numExterior = "";
    private String numInterior = "";
    private String codigoPostal = "";
    private String estadoDomicilio = "";
    private String colonia = "";
    private String idColonia = "";
    /*Datos de Negocio*/
    private String razonSocial = "";
    private boolean aceptaTerminos;
    //Datos de Negocio
    private Integer IdGiro = 0;
    private String Giro = "";
    private String nombreNegocio = "";
    private Giros giroComercio;

    private SubGiro subGiros;

    //Datos de QRs
    ArrayList<QRs> qRs;
    //Regincomplete
    boolean regincomplete =false;
    boolean breakregister =false;

    public boolean isBreakregister() {
        return breakregister;
    }

    public void setBreakregister(boolean breakregister) {
        this.breakregister = breakregister;
    }

    public static synchronized RegisterUserNew getInstance() {
        if (registerUserNew == null)
            registerUserNew = new RegisterUserNew();
        return registerUserNew;
    }

    public boolean isRegincomplete() {
        return regincomplete;
    }

    public void setRegincomplete(boolean regincomplete) {
        this.regincomplete = regincomplete;
    }

    public static RegisterUserNew getRegisterUserNew() {
        return registerUserNew;
    }

    public static void setRegisterUserNew(RegisterUserNew registerUserNew) {
        RegisterUserNew.registerUserNew = registerUserNew;
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

    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
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

    public String getIdColonia() {
        return idColonia;
    }

    public void setIdColonia(String idColonia) {
        this.idColonia = idColonia;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getGiro() {
        return Giro;
    }

    public boolean isAceptaTerminos() {
        return aceptaTerminos;
    }

    public void setAceptaTerminos(boolean aceptaTerminos) {
        this.aceptaTerminos = aceptaTerminos;
    }

    public String getFechaNacimientoToShow() {
        return fechaNacimientoToShow;
    }

    public void setFechaNacimientoToShow(String fechaNacimientoToShow) {
        this.fechaNacimientoToShow = fechaNacimientoToShow;
    }

    public String getIdEstadoNacimineto() {
        return idEstadoNacimineto;
    }

    public void setIdEstadoNacimineto(String idEstadoNacimineto) {
        this.idEstadoNacimineto = idEstadoNacimineto;
    }

    public Paises getPaisNacimiento() {
        return paisNacimiento;
    }

    public void setPaisNacimiento(Paises paisNacimiento) {
        this.paisNacimiento = paisNacimiento;
    }


    public ArrayList<QRs> getqRs() {
        return qRs;
    }

    public void setqRs(ArrayList<QRs> qRs) {
        this.qRs = qRs;
    }

    public int getStatusRegistro() {
        return statusRegistro;
    }

    public void setStatusRegistro(int statusRegistro) {
        this.statusRegistro = statusRegistro;
    }

    public Integer getIdGiro() {
        return IdGiro;
    }

    public void setIdGiro(Integer idGiro) {
        IdGiro = idGiro;
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


    public SubGiro getSubGiros() {
        return subGiros;
    }

    public void setSubGiros(SubGiro subGiros) {
        this.subGiros = subGiros;
    }
}
