package com.pagatodo.yaganaste.data.dto;

import java.io.Serializable;

/**
 * @author Juan Guerra on 17/04/2017.
 */

public class OnlineTxData implements Serializable {

    private String idFreja;
    private String typeTx;
    private String mount;
    private String reference;
    private String details;


    public String getIdFreja() {
        return idFreja;
    }

    public void setIdFreja(String idFreja) {
        this.idFreja = idFreja;
    }

    public String getTypeTx() {
        return typeTx;
    }

    public void setTypeTx(String typeTx) {
        this.typeTx = typeTx;
    }

    public String getMount() {
        return mount;
    }

    public void setMount(String mount) {
        this.mount = mount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
