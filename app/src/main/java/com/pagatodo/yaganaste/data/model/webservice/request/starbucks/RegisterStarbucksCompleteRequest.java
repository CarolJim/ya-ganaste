package com.pagatodo.yaganaste.data.model.webservice.request.starbucks;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by asandovals on 30/04/2018.
 */

public class RegisterStarbucksCompleteRequest  implements Serializable{
    @SerializedName("id_PreRegistroMovil")
    private int id_PreRegistroMovil;
    @SerializedName("udid")
    private String udid;
    @SerializedName("numeroTarjeta")
    private String numeroTarjeta;
    @SerializedName("codigoVerificador")
    private String codigoVerificador;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("genero")
    private String genero;
    @SerializedName("primerApellido")
    private String primerApellido;
    @SerializedName("segundoApellido")
    private String segundoApellido;
    @SerializedName("fechaNacimiento")
    private String fechaNacimiento;
    @SerializedName("email")
    private String email;
    @SerializedName("contrasenia")
    private String contrasenia;
    @SerializedName("telefono")
    private String telefono;
    @SerializedName("codigoPostal")
    private String codigoPostal;
    @SerializedName("Colonia")
    private String Colonia;
    @SerializedName("calleNumero")
    private String calleNumero;
    @SerializedName("bebidaFavorita")
    private String bebidaFavorita;
    @SerializedName("suscripcion")
    private boolean suscripcion;
    @SerializedName("calleyNumero")
    private String calleyNumero;
    @SerializedName("idColonia")
    private String idColonia;

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
