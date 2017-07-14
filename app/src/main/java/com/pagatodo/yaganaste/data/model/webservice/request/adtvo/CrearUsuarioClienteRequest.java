package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.request.Request;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class CrearUsuarioClienteRequest extends Request implements Serializable {

    private String Usuario = "";
    private String Contrasena = "";
    private String Nombre = "";
    private String PrimerApellido = "";
    private String SegundoApellido = "";
    private String Genero = "";
    private String FechaNacimiento = "";
    private String RFC = "";
    private String CURP = "";
    private String Nacionalidad = "";
    private String IdEstadoNacimiento;
    private String Correo = "";
    private String Telefono = "";
    private String TelefonoCelular = "";
    private String IdColonia = "";
    private String Colonia = "";
    private String CP = "";
    private String Calle = "";
    private String NumeroExterior = "";
    private String NumeroInterior = "";


    public CrearUsuarioClienteRequest() {

    }

    public CrearUsuarioClienteRequest(String usuario, String contrasena, String nombre, String primerApellido,
                                      String segundoApellido, String genero, String fechaNacimiento, String RFC,
                                      String CURP, String nacionalidad, String idEstadoNacimiento, String correo,
                                      String telefono, String telefonoCelular, String idColonia, String colonia,
                                      String CP, String calle, String numeroExterior, String numeroInterior) {
        Usuario = usuario;
        Contrasena = contrasena;
        Nombre = nombre;
        PrimerApellido = primerApellido;
        SegundoApellido = segundoApellido;
        Genero = genero;
        FechaNacimiento = fechaNacimiento;
        this.RFC = RFC;
        this.CURP = CURP;
        Nacionalidad = nacionalidad;
        IdEstadoNacimiento = idEstadoNacimiento;
        Correo = correo;
        Telefono = telefono;
        TelefonoCelular = telefonoCelular;
        IdColonia = idColonia;
        Colonia = colonia;
        this.CP = CP;
        Calle = calle;
        NumeroExterior = numeroExterior;
        NumeroInterior = numeroInterior;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPrimerApellido() {
        return PrimerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        PrimerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return SegundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        SegundoApellido = segundoApellido;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }

    public String getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        FechaNacimiento = fechaNacimiento;
    }

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getCURP() {
        return CURP;
    }

    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    public String getNacionalidad() {
        return Nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        Nacionalidad = nacionalidad;
    }

    public String getIdEstadoNacimiento() {
        return IdEstadoNacimiento;
    }

    public void setIdEstadoNacimiento(String idEstadoNacimiento) {
        IdEstadoNacimiento = idEstadoNacimiento;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getTelefonoCelular() {
        return TelefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        TelefonoCelular = telefonoCelular;
    }

    public String getIdColonia() {
        return IdColonia;
    }

    public void setIdColonia(String idColonia) {
        IdColonia = idColonia;
    }

    public String getColonia() {
        return Colonia;
    }

    public void setColonia(String colonia) {
        Colonia = colonia;
    }

    public String getCP() {
        return CP;
    }

    public void setCP(String CP) {
        this.CP = CP;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String calle) {
        Calle = calle;
    }

    public String getNumeroExterior() {
        return NumeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        NumeroExterior = numeroExterior;
    }

    public String getNumeroInterior() {
        return NumeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        NumeroInterior = numeroInterior;
    }
}
