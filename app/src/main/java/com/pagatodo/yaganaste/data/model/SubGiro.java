package com.pagatodo.yaganaste.data.model;

import java.io.Serializable;

/**
 * Created by flima on 28/04/2017.
 */

public class SubGiro implements Serializable {

    private Integer IdSubgiro = 0;
    private String Subgiro = "";

    public SubGiro() {
    }

    public SubGiro(int idSubgiro, String Subgiro) {
        this.IdSubgiro = idSubgiro;
        this.Subgiro = Subgiro;
    }

    public int getIdSubgiro() {
        return IdSubgiro;
    }

    public void setIdSubgiro(int idSubgiro) {
        IdSubgiro = idSubgiro;
    }

    public String getSubgiro() {
        return Subgiro;
    }

    public void setSubgiro(String subgiro) {
        Subgiro = subgiro;
    }

    @Override
    public boolean equals(Object v) {
        boolean retVal = false;
        if (v instanceof SubGiro) {
            SubGiro ptr = (SubGiro) v;
            retVal = ptr.IdSubgiro.intValue() == this.IdSubgiro;
        }

        return retVal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.IdSubgiro != null ? this.IdSubgiro.hashCode() : 0);
        return hash;
    }
}
