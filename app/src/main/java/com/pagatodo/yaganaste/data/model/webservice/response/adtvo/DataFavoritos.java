package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import android.os.Parcel;
import android.os.Parcelable;

import com.pagatodo.yaganaste.data.local.persistence.db.AbstractEntity;
import com.pagatodo.yaganaste.data.local.persistence.db.utils.FieldName;
import com.pagatodo.yaganaste.data.local.persistence.db.utils.Ignore;
import com.pagatodo.yaganaste.data.local.persistence.db.utils.TableName;

import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Favoritos.COLOR_MARCA;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Favoritos.ID_COMERCIO;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Favoritos.ID_CUENTA;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Favoritos.ID_FAVORITO;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Favoritos.ID_TIPO_COMERCIO;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Favoritos.IMAGEN_URL;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Favoritos.IMAGEN_URL_COMERCIO;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Favoritos.IMAGEN_URL_COMERCIO_COLOR;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Favoritos.NOMBRE;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Favoritos.NOMBRE_COMERIO;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Favoritos.REFERENCIA;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Favoritos.TABLE;

/**
 * Created by Omar on 13/09/2017.
 */
@TableName(TABLE)
public class DataFavoritos extends AbstractEntity implements Parcelable {

    @FieldName(value = ID_FAVORITO, primaryKey = true)
    private long IdFavorito;

    @FieldName(COLOR_MARCA)
    private String ColorMarca;

    @FieldName(ID_COMERCIO)
    private long IdComercio;

    @FieldName(ID_CUENTA)
    private long IdCuenta;

    @FieldName(ID_TIPO_COMERCIO)
    private int IdTipoComercio;

    @FieldName(IMAGEN_URL)
    private String ImagenURL;

    @FieldName(IMAGEN_URL_COMERCIO)
    private String ImagenURLComercio;

    @FieldName(IMAGEN_URL_COMERCIO_COLOR)
    private String ImagenURLComercioColor;

    @FieldName(NOMBRE)
    private String Nombre;

    @FieldName(NOMBRE_COMERIO)
    private String NombreComercio;

    @FieldName(REFERENCIA)
    private String Referencia;

    @Ignore
    private List<Double> listaMontos;

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

    public List<Double> getListaMontos() {
        return listaMontos;
    }

    public void setListaMontos(List<Double> listaMontos) {
        this.listaMontos = listaMontos;
    }

    public DataFavoritos() {
    }

    public DataFavoritos(String colorMarca, int idComercio, int idCuenta, int idFavorito, int idTipoComercio,
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.IdFavorito);
        dest.writeString(this.ColorMarca);
        dest.writeLong(this.IdComercio);
        dest.writeLong(this.IdCuenta);
        dest.writeInt(this.IdTipoComercio);
        dest.writeString(this.ImagenURL);
        dest.writeString(this.ImagenURLComercio);
        dest.writeString(this.ImagenURLComercioColor);
        dest.writeString(this.Nombre);
        dest.writeString(this.NombreComercio);
        dest.writeString(this.Referencia);
        dest.writeList(this.listaMontos);
    }

    protected DataFavoritos(Parcel in) {
        this.IdFavorito = in.readLong();
        this.ColorMarca = in.readString();
        this.IdComercio = in.readLong();
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

    public static final Creator<DataFavoritos> CREATOR = new Creator<DataFavoritos>() {
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
