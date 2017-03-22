package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

public class ComercioResponse implements Serializable{

    private int IdComercio;
    private int IdTipoComercio;
    private String NombreComercio = "";
    private String LogoURL = "";
    private String ImagenURL = "";
    private String ColorMarca = "";
    private List<Double> ListaMontos;
    private int LongitudReferencia;
    private String Formato = "";
    private String Mensaje = "";
    private Double Sobrecargo;


    public ComercioResponse() {

        ListaMontos = new ArrayList<Double>();
    }

    public int getIdComercio() {
        return IdComercio;
    }

    public void setIdComercio(int idComercio) {
        IdComercio = idComercio;
    }

    public int getIdTipoComercio() {
        return IdTipoComercio;
    }

    public void setIdTipoComercio(int idTipoComercio) {
        IdTipoComercio = idTipoComercio;
    }

    public String getNombreComercio() {
        return NombreComercio;
    }

    public void setNombreComercio(String nombreComercio) {
        NombreComercio = nombreComercio;
    }

    public String getLogoURL() {
        return LogoURL;
    }

    public void setLogoURL(String logoURL) {
        LogoURL = logoURL;
    }

    public String getImagenURL() {
        return ImagenURL;
    }

    public void setImagenURL(String imagenURL) {
        ImagenURL = imagenURL;
    }

    public String getColorMarca() {
        return ColorMarca;
    }

    public void setColorMarca(String colorMarca) {
        ColorMarca = colorMarca;
    }

    public List<Double> getListaMontos() {
        return ListaMontos;
    }

    public void setListaMontos(List<Double> listaMontos) {
        ListaMontos = listaMontos;
    }

    public int getLongitudReferencia() {
        return LongitudReferencia;
    }

    public void setLongitudReferencia(int longitudReferencia) {
        LongitudReferencia = longitudReferencia;
    }

    public String getFormato() {
        return Formato;
    }

    public void setFormato(String formato) {
        Formato = formato;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public Double getSobrecargo() {
        return Sobrecargo;
    }

    public void setSobrecargo(Double sobrecargo) {
        Sobrecargo = sobrecargo;
    }
}
