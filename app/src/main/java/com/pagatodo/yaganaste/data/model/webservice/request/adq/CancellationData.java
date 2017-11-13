package com.pagatodo.yaganaste.data.model.webservice.request.adq;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Jordan on 16/06/2017.
 */

public class CancellationData implements Serializable {

    @SerializedName("NoauthorizationOriginalTransaction")
    private String noauthorizationOriginalTransaction;

    @SerializedName("IdOriginalTransaction")
    private String idOriginalTransaction;

    @SerializedName("TimeOriginalTransaction")
    private String timeOriginalTransaction;

    @SerializedName("DatelOriginalTransaction")
    private String datelOriginalTransaction;

    @SerializedName("TicketOriginalTransaction")
    private String ticketOriginalTransaction;

    @SerializedName("FechaOriginalTransaction")
    private String fechaOriginalTransaction;

    public String getFechaOriginalTransaction() {
        return fechaOriginalTransaction;
    }

    public void setFechaOriginalTransaction(String fechaOriginalTransaction) {
        this.fechaOriginalTransaction = fechaOriginalTransaction;
    }

    public String getNoauthorizationOriginalTransaction() {
        return noauthorizationOriginalTransaction;
    }

    public void setNoauthorizationOriginalTransaction(String noauthorizationOriginalTransaction) {
        this.noauthorizationOriginalTransaction = noauthorizationOriginalTransaction;
    }

    public String getIdOriginalTransaction() {
        return idOriginalTransaction;
    }

    public void setIdOriginalTransaction(String idOriginalTransaction) {
        this.idOriginalTransaction = idOriginalTransaction;
    }

    public String getTimeOriginalTransaction() {
        return timeOriginalTransaction;
    }

    public void setTimeOriginalTransaction(String timeOriginalTransaction) {
        this.timeOriginalTransaction = timeOriginalTransaction;
    }

    public String getDatelOriginalTransaction() {
        return datelOriginalTransaction;
    }

    public void setDatelOriginalTransaction(String datelOriginalTransaction) {
        this.datelOriginalTransaction = datelOriginalTransaction;
    }

    public String getTicketOriginalTransaction() {
        return ticketOriginalTransaction;
    }

    public void setTicketOriginalTransaction(String ticketOriginalTransaction) {
        this.ticketOriginalTransaction = ticketOriginalTransaction;
    }
}
