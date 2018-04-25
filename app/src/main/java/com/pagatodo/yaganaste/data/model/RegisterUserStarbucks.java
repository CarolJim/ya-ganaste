package com.pagatodo.yaganaste.data.model;

/**
 * Created by asandovals on 25/04/2018.
 */

public class RegisterUserStarbucks {
    private static RegisterUserStarbucks registerUserStarbucks;
    //Preregistro

    private int id_PreRegistroMovil=0;

    //Registro
    private String udid ="";
    private String numeroTarjeta="";
    private String codigoVerificador="";
    private String nombre="";
    private String genero="";
    private String primerApellido="";
    private String segundoApellido="";
    private String fechaNacimiento="";
    private String email="";
    private String contrasenia="";
    private String telefono="";
    private String codigoPostal="";
    private String Colonia="";
    private String calleNumero="";
    private String bebidaFavorita="";
    private boolean suscripcion;
    private String calleyNumero="";
    private String idColonia="";

    public RegisterUserStarbucks() {
    }
    public static void resetRegisterUserstarbucks() {
        registerUserStarbucks = null;
    }
    public static synchronized RegisterUserStarbucks getInstance() {
        if (registerUserStarbucks == null)
            registerUserStarbucks = new RegisterUserStarbucks();
        return registerUserStarbucks;
    }

    public int getId_PreRegistroMovil() {
        return id_PreRegistroMovil;
    }

    public void setId_PreRegistroMovil(int id_PreRegistroMovil) {
        this.id_PreRegistroMovil = id_PreRegistroMovil;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getCodigoVerificador() {
        return codigoVerificador;
    }

    public void setCodigoVerificador(String codigoVerificador) {
        this.codigoVerificador = codigoVerificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getColonia() {
        return Colonia;
    }

    public void setColonia(String colonia) {
        Colonia = colonia;
    }

    public String getCalleNumero() {
        return calleNumero;
    }

    public void setCalleNumero(String calleNumero) {
        this.calleNumero = calleNumero;
    }

    public String getBebidaFavorita() {
        return bebidaFavorita;
    }

    public void setBebidaFavorita(String bebidaFavorita) {
        this.bebidaFavorita = bebidaFavorita;
    }

    public boolean isSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(boolean suscripcion) {
        this.suscripcion = suscripcion;
    }

    public String getCalleyNumero() {
        return calleyNumero;
    }

    public void setCalleyNumero(String calleyNumero) {
        this.calleyNumero = calleyNumero;
    }

    public String getIdColonia() {
        return idColonia;
    }

    public void setIdColonia(String idColonia) {
        this.idColonia = idColonia;
    }
}
