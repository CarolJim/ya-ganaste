package com.pagatodo.yaganaste.data.model.webservice.response.adtvo;

import com.google.gson.annotations.SerializedName;
import com.pagatodo.yaganaste.data.local.persistence.db.AbstractEntity;
import com.pagatodo.yaganaste.data.local.persistence.db.utils.FieldName;
import com.pagatodo.yaganaste.data.local.persistence.db.utils.Ignore;
import com.pagatodo.yaganaste.data.local.persistence.db.utils.TableName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Comercios.COLOR_MARCA;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Comercios.COMERCIO;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Comercios.FORMATO;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Comercios.ID_COMERCIO;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Comercios.ID_TIPO_COMERCIO;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Comercios.LONGITUD_REFERENCIA;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Comercios.MENSAJE;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Comercios.ORDEN;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Comercios.SOBRECARGO;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Comercios.TABLE;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Comercios.URL_IMAGEN;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Comercios.URL_LOGO;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Comercios.URL_LOGO_COLOR;

/**
 * Created by flima on 21/03/2017.
 */

@TableName(TABLE)
public class ComercioResponse extends AbstractEntity implements Serializable {

    @SerializedName("IdComercio")
    @FieldName(value = ID_COMERCIO, primaryKey = true)
    private int idComercio;

    @SerializedName("IdTipoComercio")
    @FieldName(ID_TIPO_COMERCIO)
    private int idTipoComercio;

    @SerializedName("NombreComercio")
    @FieldName(COMERCIO)
    private String nombreComercio;

    @SerializedName("LogoURL")
    @FieldName(URL_LOGO)
    private String logoURL;

    @SerializedName("LogoURLColor")
    @FieldName(URL_LOGO_COLOR)
    private String logoURLColor;

    @SerializedName("ImagenURL")
    @FieldName(URL_IMAGEN)
    private String imagenURL;

    @SerializedName("ColorMarca")
    @FieldName(COLOR_MARCA)
    private String colorMarca;

    @SerializedName("ListaMontos")
    @Ignore
    private List<Double> listaMontos;

    @SerializedName("LongitudReferencia")
    @FieldName(LONGITUD_REFERENCIA)
    private int longitudReferencia;

    @SerializedName("Formato")
    @FieldName(FORMATO)
    private String formato;

    @SerializedName("Mensaje")
    @FieldName(MENSAJE)
    private String mensaje;

    @SerializedName("Orden")
    @FieldName(ORDEN)
    private int orden;

    @SerializedName("SobreCargo")
    @FieldName(SOBRECARGO)
    private Double sobrecargo;


    public ComercioResponse() {

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
