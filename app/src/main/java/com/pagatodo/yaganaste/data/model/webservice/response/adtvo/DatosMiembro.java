package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.starbucks.PreferenciasStarbucks;

import java.io.Serializable;

/**
 * Created by asandovals on 20/04/2018.
 */

public class DatosMiembro implements Serializable {

    private String bebidaFavorita = "";
    private String calleNumero = "";
    private String calleyNumero = "";
    private String ciudad = "";
    private String codigoPostal = "";
    private String colonia = "";
    private String email = "";
    private String esCLientePrevio = "";
    private String estado = "";
    private String fechaNacimiento = "";
    private String genero = "";
    private String miembroDesde = "";
    private String municipio = "";
    private String nombre = "";
    private PreferenciasStarbucks preferenciasStarbucks;
    private String primerApellido = "";
    private String segundoApellido = "";
    private int statusGold;
    private String telefono = "";

    public String getBebidaFavorita() {
        return bebidaFavorita;
    }

    public void setBebidaFavorita(String bebidaFavorita) {
        this.bebidaFavorita = bebidaFavorita;
    }

    public String getCalleNumero() {
        return calleNumero;
    }

    public void setCalleNumero(String calleNumero) {
        this.calleNumero = calleNumero;
    }

    public String getCalleyNumero() {
        return calleyNumero;
    }

    public void setCalleyNumero(String calleyNumero) {
        this.calleyNumero = calleyNumero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEsCLientePrevio() {
        return esCLientePrevio;
    }

    public void setEsCLientePrevio(String esCLientePrevio) {
        this.esCLientePrevio = esCLientePrevio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getMiembroDesde() {
        return miembroDesde;
    }

    public void setMiembroDesde(String miembroDesde) {
        this.miembroDesde = miembroDesde;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public PreferenciasStarbucks getPreferenciasStarbucks() {
        return preferenciasStarbucks;
    }

    public void setPreferenciasStarbucks(PreferenciasStarbucks preferenciasStarbucks) {
        this.preferenciasStarbucks = preferenciasStarbucks;
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

    public int getStatusGold() {
        return statusGold;
    }

    public void setStatusGold(int statusGold) {
        this.statusGold = statusGold;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
