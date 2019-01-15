package com.pagatodo.yaganaste.data.room_db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import android.os.Parcel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar on 13/09/2017.
 */
@Entity
public class Favoritos implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id_favorito")
    private long IdFavorito;

    @ColumnInfo(name = "color_marca")
    private String ColorMarca;

    @ColumnInfo(name = "id_comercio")
    private int IdComercio;

    @ColumnInfo(name = "id_cuenta")
    private long IdCuenta;

    @ColumnInfo(name = "id_tipo_comercio")
    private int IdTipoComercio;

    @ColumnInfo(name = "url_imagen")
    private String ImagenURL;

    @ColumnInfo(name = "url_imagen_comercio")
    private String ImagenURLComercio;

    @ColumnInfo(name = "url_imagen_comercio_color")
    private String ImagenURLComercioColor;

    @ColumnInfo(name = "nombre")
    private String Nombre;

    @ColumnInfo(name = "nombre_comercio")
    private String NombreComercio;

    @ColumnInfo(name = "referencia")
    private String Referencia;

    @Ignore
    private List<Double> listaMontos;

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

    public long getIdCuenta() {
        return IdCuenta;
    }

    public void setIdCuenta(long idCuenta) {
        IdCuenta = idCuenta;
    }

    public long getIdFavorito() {
        return IdFavorito;
    }

    public void setIdFavorito(long idFavorito) {
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

    public List<Double> getListaMontos() {
        return listaMontos;
    }

    public void setListaMontos(List<Double> listaMontos) {
        this.listaMontos = listaMontos;
    }

    public Favoritos() {
    }

    public Favoritos(String colorMarca, int idComercio, int idCuenta, int idFavorito, int idTipoComercio,
                     String imagenURL, String imagenURLComercio, String imagenURLComercioColor,
                     String nombre, String nombreComercio, String referencia) {
        this.ColorMarca = colorMarca;
        this.IdComercio = idComercio;
        this.IdCuenta = idCuenta;
        this.IdFavorito = idFavorito;
        this.IdTipoComercio = idTipoComercio;
        this.ImagenURL = imagenURL;
        this.ImagenURLComercio = imagenURLComercio;
        this.ImagenURLComercioColor = imagenURLComercioColor;
        this.Nombre = nombre;
        this.NombreComercio = nombreComercio;
        this.Referencia = referencia;
    }

    public Favoritos(int idComercio) {
        listaMontos = new ArrayList<Double>();
        this.IdComercio = idComercio;
        this.IdTipoComercio = 0;
        this.NombreComercio = "";
        this.ImagenURLComercio = "";
        this.ImagenURL = "";
        this.ColorMarca = "#00FFFFFF";
    }

    protected Favoritos(Parcel in) {
        this.IdFavorito = in.readLong();
        this.ColorMarca = in.readString();
        this.IdComercio = in.readInt();
        this.IdCuenta = in.readLong();
        this.IdTipoComercio = in.readInt();
        this.ImagenURL = in.readString();
        this.ImagenURLComercio = in.readString();
        this.ImagenURLComercioColor = in.readString();
        this.Nombre = in.readString();
        this.NombreComercio = in.readString();
        this.Referencia = in.readString();
        this.listaMontos = new ArrayList<Double>();
        in.readList(this.listaMontos, Double.class.getClassLoader());
    }
}
