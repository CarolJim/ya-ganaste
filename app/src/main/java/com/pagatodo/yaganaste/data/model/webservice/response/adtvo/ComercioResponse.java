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
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Comercios.SOBRECARGO;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Comercios.TABLE;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Comercios.URL_IMAGEN;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.Comercios.URL_LOGO;

/**
 * Created by flima on 21/03/2017.
 */

@TableName(TABLE)
public class ComercioResponse extends AbstractEntity implements Serializable {

    @FieldName(value = ID_COMERCIO, primaryKey = true)
    private int IdComercio;

    @FieldName(ID_TIPO_COMERCIO)
    private int IdTipoComercio;

    @FieldName(COMERCIO)
    private String NombreComercio = "";

    @FieldName(URL_LOGO)
    private String LogoURL = "";

    @FieldName(URL_IMAGEN)
    private String ImagenURL = "";

    @FieldName(COLOR_MARCA)
    private String ColorMarca = "";

    @Ignore
    private List<Double> ListaMontos;

    @FieldName(LONGITUD_REFERENCIA)
    private int LongitudReferencia;

    @FieldName(FORMATO)
    private String Formato = "";

    @FieldName(MENSAJE)
    private String Mensaje = "";

    @SerializedName("SobreCargo")
    @FieldName(SOBRECARGO)
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

    public ArrayList<String> getMontosStringList() {
        ArrayList<String> response = new ArrayList<>();
        for (Double m : ListaMontos) {
            response.add("$" + m.toString() + ".00");
        }

        return response;
    }
}
