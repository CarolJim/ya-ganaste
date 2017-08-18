package com.pagatodo.yaganaste.data.model.webservice.response.cupo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Tato on 17/08/17.
 */

public class RefereciasResponse implements Serializable {

    private String EstatusReferencia;
    private int IdEstatusReferencia;
    private int IdRelacion;
    private int IdTipoReferencia;
    private String Nombre;
    private String PrimerApellido;
    private String ProductoServicioProveedor;
    private String SegundoApellido;
    private String Telefono;

    public  RefereciasResponse() {
    }

    public String getEstatusReferencia() {
        return EstatusReferencia;
    }

    public void setEstatusReferencia(String estatusReferencia) {
        EstatusReferencia = estatusReferencia;
    }

    public int getIdEstatusReferencia() {
        return IdEstatusReferencia;
    }

    public void setIdEstatusReferencia(int idEstatusReferencia) {
        IdEstatusReferencia = idEstatusReferencia;
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

    public String getProductoServicioProveedor() {
        return ProductoServicioProveedor;
    }

    public void setProductoServicioProveedor(String productoServicioProveedor) {
        ProductoServicioProveedor = productoServicioProveedor;
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
}
