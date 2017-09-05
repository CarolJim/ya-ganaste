package com.pagatodo.yaganaste.data.model;

/**
 * Created by Horacio on 28/08/17.
 */

public class Referencias {

    private static Referencias referencias;

    // Refrencia Familiar

    private String familiarNombre = "";
    private String familiarApellidoPaterno = "";
    private String familiarApellidoMaterno = "";
    private String familiarTelefono = "";
    private String familiarRelacion = "";
    private int    familiarIdRelacion;
    private Boolean familiarActualizado = false;


    // Refrencia Personal

    private String personalNombre = "";
    private String personalApellidoPaterno = "";
    private String personalApellidoMaterno = "";
    private String personalTelefono = "";
    private String personalRelacion = "";
    private int    personalIdRelacion;
    private Boolean personaActualizado = false;

    // Refrencia Proveedor

    private String proveedorNombre = "";
    private String proveedorApellidoPaterno = "";
    private String proveedorApellidoMaterno = "";
    private String proveedorTelefono = "";
    private String proveedorProductoServicio = "";
    private Boolean proveedorActualizado = false;

    private Referencias() {

    }

    public static synchronized Referencias getInstance() {
        if (referencias == null){
            referencias = new Referencias();
        }
        return referencias;
    }

    public static Referencias getReferencias() {
        return referencias;
    }

    public static void setReferencias(Referencias referencias) {
        Referencias.referencias = referencias;
    }

    public String getFamiliarNombre() {
        return familiarNombre;
    }

    public void setFamiliarNombre(String familiarNombre) {
        this.familiarNombre = familiarNombre;
    }

    public String getFamiliarApellidoPaterno() {
        return familiarApellidoPaterno;
    }

    public void setFamiliarApellidoPaterno(String familiarApellidoPaterno) {
        this.familiarApellidoPaterno = familiarApellidoPaterno;
    }

    public String getFamiliarApellidoMaterno() {
        return familiarApellidoMaterno;
    }

    public void setFamiliarApellidoMaterno(String familiarApellidoMaterno) {
        this.familiarApellidoMaterno = familiarApellidoMaterno;
    }

    public String getFamiliarTelefono() {
        return familiarTelefono;
    }

    public void setFamiliarTelefono(String familiarTelefono) {
        this.familiarTelefono = familiarTelefono;
    }

    public String getFamiliarRelacion() {
        return familiarRelacion;
    }

    public void setFamiliarRelacion(String familiarRelacion) {
        this.familiarRelacion = familiarRelacion;
    }

    public int getFamiliarIdRelacion() {
        return familiarIdRelacion;
    }

    public void setFamiliarIdRelacion(int familiarIdRelacion) {
        this.familiarIdRelacion = familiarIdRelacion;
    }

    public String getPersonalNombre() {
        return personalNombre;
    }

    public void setPersonalNombre(String personalNombre) {
        this.personalNombre = personalNombre;
    }

    public String getPersonalApellidoPaterno() {
        return personalApellidoPaterno;
    }

    public void setPersonalApellidoPaterno(String personalApellidoPaterno) {
        this.personalApellidoPaterno = personalApellidoPaterno;
    }

    public String getPersonalApellidoMaterno() {
        return personalApellidoMaterno;
    }

    public void setPersonalApellidoMaterno(String personalApellidoMaterno) {
        this.personalApellidoMaterno = personalApellidoMaterno;
    }

    public String getPersonalTelefono() {
        return personalTelefono;
    }

    public void setPersonalTelefono(String personalTelefono) {
        this.personalTelefono = personalTelefono;
    }

    public String getPersonalRelacion() {
        return personalRelacion;
    }

    public void setPersonalRelacion(String personalRelacion) {
        this.personalRelacion = personalRelacion;
    }

    public int getPersonalIdRelacion() {
        return personalIdRelacion;
    }

    public void setPersonalIdRelacion(int personalIdRelacion) {
        this.personalIdRelacion = personalIdRelacion;
    }

    public Boolean getPersonaActualizado() {
        return personaActualizado;
    }

    public void setPersonaActualizado(Boolean personaActualizado) {
        this.personaActualizado = personaActualizado;
    }

    public String getProveedorNombre() {
        return proveedorNombre;
    }

    public void setProveedorNombre(String proveedorNombre) {
        this.proveedorNombre = proveedorNombre;
    }

    public String getProveedorApellidoPaterno() {
        return proveedorApellidoPaterno;
    }

    public void setProveedorApellidoPaterno(String proveedorApellidoPaterno) {
        this.proveedorApellidoPaterno = proveedorApellidoPaterno;
    }

    public String getProveedorApellidoMaterno() {
        return proveedorApellidoMaterno;
    }

    public void setProveedorApellidoMaterno(String proveedorApellidoMaterno) {
        this.proveedorApellidoMaterno = proveedorApellidoMaterno;
    }

    public String getProveedorTelefono() {
        return proveedorTelefono;
    }

    public void setProveedorTelefono(String proveedorTelefono) {
        this.proveedorTelefono = proveedorTelefono;
    }

    public String getProveedorProductoServicio() {
        return proveedorProductoServicio;
    }

    public void setProveedorProductoServicio(String proveedorProductoServicio) {
        this.proveedorProductoServicio = proveedorProductoServicio;
    }

    public Boolean getFamiliarActualizado() {
        return familiarActualizado;
    }

    public void setFamiliarActualizado(Boolean familiarActualizado) {
        this.familiarActualizado = familiarActualizado;
    }


    public Boolean getProveedorActualizado() {
        return proveedorActualizado;
    }

    public void setProveedorActualizado(Boolean proveedorActualizado) {
        this.proveedorActualizado = proveedorActualizado;
    }
}
