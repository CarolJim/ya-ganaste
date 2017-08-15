package com.pagatodo.yaganaste.data.model;

import com.pagatodo.yaganaste.data.model.db.Countries;

/**
 * Created by Tato on 07/08/17.
 */

public class RegisterCupo {

    private static RegisterCupo registerCupo;

    // Datos Cuentanos Mas
    private String estadoCivil = "";
    private int idEstadoCivil;
    private String hijos = "";
    private int idHijos;
    private Boolean creditoBancario;
    private Boolean creditoAutomotriz;
    private Boolean tarjetaCreditoBancario;
    private String  numeroTarjeta = "";

    // Refrencia Familiar

    private String familiarNombre = "";
    private String familiarApellidoPaterno = "";
    private String familiarApellidoMaterno = "";
    private String familiarTelefono = "";
    private String familiarRelacion = "";
    private int    familiarIdRelacion;


    // Refrencia Personal

    private String personalNombre = "";
    private String personalApellidoPaterno = "";
    private String personalApellidoMaterno = "";
    private String personalTelefono = "";
    private String personalRelacion = "";
    private int    personalIdRelacion;

    // Refrencia Proveedor

    private String proveedorNombre = "";
    private String proveedorApellidoPaterno = "";
    private String proveedorApellidoMaterno = "";
    private String proveedorTelefono = "";
    private String proveedorProductoServicio = "";

    //Datos Domicilio Actual
    private String calle = "";
    private String numExterior = "";
    private String numInterior = "";
    private String codigoPostal = "";
    private String estadoDomicilio = "";
    private String colonia = "";
    private String idColonia = "";
    private Countries paisNacimiento;
    private String nacionalidad = "";
    private String lugarNacimiento = "";
    private String idEstadoNacimineto = "";

    private RegisterCupo() {

    }

    public Countries getPaisNacimiento() {
        return paisNacimiento;
    }

    public void setPaisNacimiento(Countries paisNacimiento) {
        this.paisNacimiento = paisNacimiento;
        this.nacionalidad = paisNacimiento.getIdPais();
    }

    public static synchronized RegisterCupo getInstance() {
        if (registerCupo == null)
            registerCupo = new RegisterCupo();
        return registerCupo;
    }


    public static RegisterCupo getRegisterCupo() {
        return registerCupo;
    }

    public static void setRegisterCupo(RegisterCupo registerCupo) {
        RegisterCupo.registerCupo = registerCupo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public int getIdEstadoCivil() {
        return idEstadoCivil;
    }

    public void setIdEstadoCivil(int idEstadoCivil) {
        this.idEstadoCivil = idEstadoCivil;
    }

    public String getHijos() {
        return hijos;
    }

    public void setHijos(String hijos) {
        this.hijos = hijos;
    }

    public int getIdHijos() {
        return idHijos;
    }

    public void setIdHijos(int idHijos) {
        this.idHijos = idHijos;
    }

    public Boolean getCreditoBancario() {
        return creditoBancario;
    }

    public void setCreditoBancario(Boolean creditoBancario) {
        this.creditoBancario = creditoBancario;
    }

    public Boolean getCreditoAutomotriz() {
        return creditoAutomotriz;
    }

    public void setCreditoAutomotriz(Boolean creditoAutomotriz) {
        this.creditoAutomotriz = creditoAutomotriz;
    }

    public Boolean getTarjetaCreditoBancario() {
        return tarjetaCreditoBancario;
    }

    public void setTarjetaCreditoBancario(Boolean tarjetaCreditoBancario) {
        this.tarjetaCreditoBancario = tarjetaCreditoBancario;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
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

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getLugarNacimiento() {
        return lugarNacimiento;
    }

    public void setLugarNacimiento(String lugarNacimiento) {
        this.lugarNacimiento = lugarNacimiento;
    }

    public String getIdEstadoNacimineto() {
        return idEstadoNacimineto;
    }

    public void setIdEstadoNacimineto(String idEstadoNacimineto) {
        this.idEstadoNacimineto = idEstadoNacimineto;
    }
}
