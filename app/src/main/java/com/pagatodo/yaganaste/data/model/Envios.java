package com.pagatodo.yaganaste.data.model;

import com.pagatodo.yaganaste.data.model.webservice.response.adtvo.ComercioResponse;
import com.pagatodo.yaganaste.interfaces.enums.TransferType;

/**
 * Created by Jordan on 26/04/2017.
 */

public class Envios extends Payments {

    private TransferType tipoEnvio;
    private String nombreDestinatario;
    private String referenciaNumerica;

    public Envios() {
        super();
    }

    public Envios(TransferType tipo, String destino, Double importe, String nombreDestinatario,
                  String concepto, String referenceNumber, ComercioResponse comercio, boolean isFavorite) {
        this.tipoEnvio = tipo;
        this.referencia = destino;
        this.setMonto(importe);
        this.nombreDestinatario = nombreDestinatario;
        this.concepto = concepto;
        this.referenciaNumerica = referenceNumber;
        this.comercio = comercio;
        this.isFavorite = isFavorite;
    }

    public TransferType getTipoEnvio() {
        return tipoEnvio;
    }

    public void setTipoEnvio(TransferType tipoEnvio) {
        this.tipoEnvio = tipoEnvio;
    }

    public String getNombreDestinatario() {
        return nombreDestinatario;
    }

    public void setNombreDestinatario(String nombreDestinatario) {
        this.nombreDestinatario = nombreDestinatario;
    }

    public String getReferenciaNumerica() {
        return referenciaNumerica;
    }

    public void setReferenciaNumerica(String referenciaNumerica) {
        this.referenciaNumerica = referenciaNumerica;
    }
}
