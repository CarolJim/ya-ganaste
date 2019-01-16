package com.pagatodo.yaganaste.data.room_db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flima on 21/03/2017.
 */

@Entity
public class Comercio implements Serializable {

    @SerializedName("IdComercio")
    @PrimaryKey
    @ColumnInfo(name = "id_comercio")
    private int idComercio;

    @SerializedName("IdTipoComercio")
    @ColumnInfo(name = "id_tipo_comercio")
    private int idTipoComercio;

    @SerializedName("NombreComercio")
    @ColumnInfo(name = "nombre_comercio")
    private String nombreComercio;

    @SerializedName("LogoURL")
    @ColumnInfo(name = "url_logo")
    private String logoURL;

    @SerializedName("LogoURLColor")
    @ColumnInfo(name = "url_logo_color")
    private String logoURLColor;

    @SerializedName("ImagenURL")
    @ColumnInfo(name = "url_imagen")
    private String imagenURL;

    @SerializedName("ColorMarca")
    @ColumnInfo(name = "color_marca")
    private String colorMarca;

    @SerializedName("ListaMontos")
    @Ignore
    private List<Double> listaMontos;

    @SerializedName("LongitudReferencia")
    @ColumnInfo(name = "longitud_referencia")
    private int longitudReferencia;

    @SerializedName("Formato")
    @ColumnInfo(name = "formato")
    private String formato;

    @SerializedName("Mensaje")
    @ColumnInfo(name = "mensaje")
    private String mensaje;

    @SerializedName("Orden")
    @ColumnInfo(name = "orden")
    private int orden;

    @SerializedName("SobreCargo")
    @ColumnInfo(name = "sobrecargo")
    private Double sobrecargo;

    public Comercio() {
        listaMontos = new ArrayList<Double>();
        this.idComercio = 0;
        this.idTipoComercio = 0;
        this.nombreComercio = "";
        this.logoURL = "";
        this.imagenURL = "";
        this.colorMarca = "#00FFFFFF";
        this.longitudReferencia = 10;
        this.formato = "";
        this.mensaje = "";
        this.sobrecargo = 0.0;
    }

    @Ignore
    public Comercio(int idComercio, int idTipoComercio, String nombreComercio, int longitudReferencia, String formato) {
        this.idComercio = idComercio;
        this.idTipoComercio = idTipoComercio;
        this.nombreComercio = nombreComercio;
        this.longitudReferencia = longitudReferencia;
        this.formato = formato;
    }

    @Ignore
    public Comercio(int idComercio) {
        listaMontos = new ArrayList<Double>();
        this.idComercio = idComercio;
        this.idTipoComercio = 0;
        this.nombreComercio = "";
        this.logoURL = "";
        this.imagenURL = "";
        this.colorMarca = "#00FFFFFF";
        this.longitudReferencia = 10;
        this.formato = "";
        this.mensaje = "";
        this.sobrecargo = 0.0;
    }

    public int getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(int idComercio) {
        this.idComercio = idComercio;
    }

    public int getIdTipoComercio() {
        return idTipoComercio;
    }

    public void setIdTipoComercio(int idTipoComercio) {
        this.idTipoComercio = idTipoComercio;
    }

    public String getNombreComercio() {
        return nombreComercio;
    }

    public void setNombreComercio(String nombreComercio) {
        this.nombreComercio = nombreComercio;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public String getImagenURL() {
        return imagenURL;
    }

    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }

    public String getColorMarca() {
        return colorMarca;
    }

    public void setColorMarca(String colorMarca) {
        this.colorMarca = colorMarca;
    }

    public List<Double> getListaMontos() {
        return listaMontos;
    }

    public void setListaMontos(List<Double> listaMontos) {
        this.listaMontos = listaMontos;
    }

    public int getLongitudReferencia() {
        return longitudReferencia;
    }

    public void setLongitudReferencia(int longitudReferencia) {
        this.longitudReferencia = longitudReferencia;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Double getSobrecargo() {
        return sobrecargo;
    }

    public void setSobrecargo(Double sobrecargo) {
        this.sobrecargo = sobrecargo;
    }

    public ArrayList<String> getMontosStringList() {
        ArrayList<String> response = new ArrayList<>();
        for (Double m : listaMontos) {
            response.add("$" + m.toString() + ".00");
        }

        return response;
    }

    public String getLogoURLColor() {
        return logoURLColor;
    }

    public void setLogoURLColor(String logoURLColor) {
        this.logoURLColor = logoURLColor;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
}
