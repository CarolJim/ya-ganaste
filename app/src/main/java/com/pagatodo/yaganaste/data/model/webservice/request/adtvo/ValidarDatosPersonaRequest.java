package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author jguerras  on 29/06/2017.
 */

public class ValidarDatosPersonaRequest implements Serializable {


    @SerializedName("Nombre")
    private String nombre;

    @SerializedName("PrimerApellido")
    private String primerApellido;

    @SerializedName("SegundoApellido")
    private String segundoApellido;

    @SerializedName("Genero")
    private String genero;

    @SerializedName("FechaNacimiento")
    private String fechaNacimiento;

    @SerializedName("ClaveEntidadNacimiento")
    private String claveEntidadNacimiento;

    @SerializedName("CURP")
    private String CURP;

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getClaveEntidadNacimiento() {
        return claveEntidadNacimiento;
    }

    public void setClaveEntidadNacimiento(String claveEntidadNacimiento) {
        this.claveEntidadNacimiento = claveEntidadNacimiento;
    }
}