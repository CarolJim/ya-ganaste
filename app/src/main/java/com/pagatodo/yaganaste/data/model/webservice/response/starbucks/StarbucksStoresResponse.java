package com.pagatodo.yaganaste.data.model.webservice.response.starbucks;

import java.io.Serializable;
import java.util.List;

public class StarbucksStoresResponse implements Serializable {

    private int codigo;
    private String descripcion;
    private List<StarbucksStores> stores;

    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<StarbucksStores> getStores() {
        return stores;
    }
}
