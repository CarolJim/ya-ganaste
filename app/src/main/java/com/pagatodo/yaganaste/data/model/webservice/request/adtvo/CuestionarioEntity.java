package com.pagatodo.yaganaste.data.model.webservice.request.adtvo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by flima on 21/03/2017.
 */

public class CuestionarioEntity implements Serializable {

    @SerializedName("PreguntaId")
    private int preguntaId;
    @SerializedName("Valor")
    private boolean valor;
    @SerializedName("IdCatalogo")
    private int idCatalogo;
    @SerializedName("TextoAbierto")
    private String textoAbierto;

    public CuestionarioEntity() {
    }

    public CuestionarioEntity(int preguntaId, boolean valor) {
        this.preguntaId = preguntaId;
        this.valor = valor;
    }

    public CuestionarioEntity(int preguntaId, boolean valor, int idCatalogo) {
        this.preguntaId = preguntaId;
        this.valor = valor;
        this.idCatalogo = idCatalogo;
    }

    public CuestionarioEntity(int preguntaId, boolean valor, int idCatalogo, String textoAbierto) {
        this.preguntaId = preguntaId;
        this.valor = valor;
        this.idCatalogo = idCatalogo;
        this.textoAbierto = textoAbierto;
    }

    public int getPreguntaId() {
        return preguntaId;
    }

    public void setPreguntaId(int preguntaId) {
        this.preguntaId = preguntaId;
    }

    public boolean isValor() {
        return valor;
    }

    public void setValor(boolean valor) {
        this.valor = valor;
    }

    public int getIdCatalogo() {
        return idCatalogo;
    }

    public void setIdCatalogo(int idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public String getTextoAbierto() {
        return textoAbierto;
    }

    public void setTextoAbierto(String textoAbierto) {
        this.textoAbierto = textoAbierto;
    }

    @Override
    public String toString() {
        return "CuestionarioEntity{" +
                "preguntaId=" + preguntaId +
                ", valor=" + valor +
                ", idCatalogo=" + idCatalogo +
                ", textoAbierto='" + textoAbierto + '\'' +
                '}';
    }
}
