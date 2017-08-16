package com.pagatodo.yaganaste.data.dto;

import com.google.gson.annotations.SerializedName;
import com.pagatodo.yaganaste.interfaces.enums.OnlineTypes;

import java.io.Serializable;

/**
 * @author Juan Guerra on 17/04/2017.
 */

public class OnlineTxData implements Serializable {

    @SerializedName("IdTipoTransaccion")
    private OnlineTypes idTipoTransaccion;
    @SerializedName("EnvioPush")
    private boolean envioPush;
    @SerializedName("DataTransferenciasBancarias")
    private DatosTransferencia datosTransferencia;
    @SerializedName("DataAgregarFavoritos")
    private Object dataAgregarFavoritos;
    @SerializedName("DataActualizarFavorito")
    private Object dataActualizarFavorito;
    @SerializedName("DataEstadoCuenta")
    private Object dataEstadoCuenta;

    private String idFreja;

    public OnlineTypes getIdTipoTransaccion() {
        return idTipoTransaccion;
    }
    public boolean isEnvioPush() {
        return envioPush;
    }
    public DatosTransferencia getDatosTransferencia() {
        return datosTransferencia;
    }
    public Object getDataAgregarFavoritos() {
        return dataAgregarFavoritos;
    }
    public Object getDataActualizarFavorito() {
        return dataActualizarFavorito;
    }
    public Object getDataEstadoCuenta() {
        return dataEstadoCuenta;
    }

    public String getIdFreja() {
        return idFreja;
    }



    public static class DatosTransferencia implements Serializable {
        @SerializedName("IdTipoTransaccion")
        private OnlineTypes typeTx;
        @SerializedName("Ticket")
        private String ticket;
        @SerializedName("Referencia")
        private String reference;
        @SerializedName("Monto")
        private double mount;
        @SerializedName("IdComercioAfectado")
        private int idComercioAfectado;
        @SerializedName("NombreBeneficiario")
        private String nombreBeneficiario;
        @SerializedName("Concepto")
        private String concepto;
        @SerializedName("ReferenciaNumerica")
        private String referenciaNumerica;

        public OnlineTypes getTypeTx() {
            return typeTx;
        }
        public String getTicket() {
            return ticket;
        }
        public String getReference() {
            return reference;
        }
        public double getMount() {
            return mount;
        }
        public int getIdComercioAfectado() {
            return idComercioAfectado;
        }
        public String getNombreBeneficiario() {
            return nombreBeneficiario;
        }
        public String getConcepto() {
            return concepto;
        }
        public String getReferenciaNumerica() {
            return referenciaNumerica;
        }
    }




}
