package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Omar on 13/09/2017.
 */

public class DataFavoritos implements Parcelable {

    private String ColorMarca;
    private long IdComercio;
    private long IdCuenta;
    private long IdFavorito;
    private int IdTipoComercio;
    private String ImagenURL;
    private String ImagenURLComercio;
    private String ImagenURLComercioColor;
    private String Nombre;
    private String NombreComercio;
    private String Referencia;

    public String getColorMarca() {
        return ColorMarca;
    }

    public void setColorMarca(String colorMarca) {
        ColorMarca = colorMarca;
    }

    public long getIdComercio() {
        return IdComercio;
    }

    public void setIdComercio(long idComercio) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ColorMarca);
        dest.writeLong(this.IdComercio);
        dest.writeLong(this.IdCuenta);
        dest.writeLong(this.IdFavorito);
        dest.writeInt(this.IdTipoComercio);
        dest.writeString(this.ImagenURL);
        dest.writeString(this.ImagenURLComercio);
        dest.writeString(this.ImagenURLComercioColor);
        dest.writeString(this.Nombre);
        dest.writeString(this.NombreComercio);
        dest.writeString(this.Referencia);
    }

    public DataFavoritos() {
    }

    protected DataFavoritos(Parcel in) {
        this.ColorMarca = in.readString();
        this.IdComercio = in.readLong();
        this.IdCuenta = in.readLong();
        this.IdFavorito = in.readLong();
        this.IdTipoComercio = in.readInt();
        this.ImagenURL = in.readString();
        this.ImagenURLComercio = in.readString();
        this.ImagenURLComercioColor = in.readString();
        this.Nombre = in.readString();
        this.NombreComercio = in.readString();
        this.Referencia = in.readString();
    }

    public static final Parcelable.Creator<DataFavoritos> CREATOR = new Parcelable.Creator<DataFavoritos>() {
        @Override
        public DataFavoritos createFromParcel(Parcel source) {
            return new DataFavoritos(source);
        }

        @Override
        public DataFavoritos[] newArray(int size) {
            return new DataFavoritos[size];
        }
    };
}
