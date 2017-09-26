package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.pagatodo.yaganaste.data.model.webservice.response.manager.GenericResponse;

import java.io.Serializable;

/**
 * Created by Francisco Manzo on 21/08/2017.
 */

public class AddFavoritosResponse implements Serializable {

    private String ColorMarca = "";
    private int IdComercio = 0;
    private int IdCuenta = 0;
    private int IdFavorito = 0;
    private int IdTipoComercio = 0;
    private String ImagenURL = "";
    private String ImagenURLComercio = "";
    private String ImagenURLComercioColor = "";
    private String Nombre = "";
    private String NombreComercio = "";
    private String Referencia = "";

    public AddFavoritosResponse() {
    }

    public String getColorMarca() {
        return ColorMarca;
    }

    public void setColorMarca(String colorMarca) {
        ColorMarca = colorMarca;
    }

    public int getIdComercio() {
        return IdComercio;
    }

    public void setIdComercio(int idComercio) {
        IdComercio = idComercio;
    }

    public int getIdCuenta() {
        return IdCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        IdCuenta = idCuenta;
    }

    public int getIdFavorito() {
        return IdFavorito;
    }

    public void setIdFavorito(int idFavorito) {
        IdFavorito = idFavorito;
    }

    public int getIdTipoComercio() {
        return IdTipoComercio;
    }

    public void setIdTipoComercio(int idTipoComercio) {
        IdTipoComercio = idTipoComercio;
    }

    public String getImagenURL() {
        return ImagenURL;
    }

    public void setImagenURL(String imagenURL) {
        ImagenURL = imagenURL;
    }

    public String getImagenURLComercio() {
        return ImagenURLComercio;
    }

    public void setImagenURLComercio(String imagenURLComercio) {
        ImagenURLComercio = imagenURLComercio;
    }

    public String getImagenURLComercioColor() {
        return ImagenURLComercioColor;
    }

    public void setImagenURLComercioColor(String imagenURLComercioColor) {
        ImagenURLComercioColor = imagenURLComercioColor;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getNombreComercio() {
        return NombreComercio;
    }

    public void setNombreComercio(String nombreComercio) {
        NombreComercio = nombreComercio;
    }

    public String getReferencia() {
        return Referencia;
    }

    public void setReferencia(String referencia) {
        Referencia = referencia;
    }
}
