package com.pagatodo.yaganaste.data.model.webservice.request.cupo;

import java.io.Serializable;

/**
 * Created by Tato on 09/08/17.
 */

public class CupoReferencia implements Serializable {

    private String Nombre = "";
    private String PrimerApellido = "";
    private String SegundoApellido = "";
    private String Telefono = "";
    private int    IdRelacion = 0;
    private int    IdTipoReferencia = 0;
    private String ProductoServicioProveedor = "";


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

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public int getIdRelacion() {
        return IdRelacion;
    }

    public void setIdRelacion(int idRelacion) {
        IdRelacion = idRelacion;
    }

    public int getIdTipoReferencia() {
        return IdTipoReferencia;
    }

    public void setIdTipoReferencia(int idTipoReferencia) {
        IdTipoReferencia = idTipoReferencia;
    }

    public String getProductoServicioProveedor() {
        return ProductoServicioProveedor;
    }

    public void setProductoServicioProveedor(String productoServicioProveedor) {
        ProductoServicioProveedor = productoServicioProveedor;
    }
}
