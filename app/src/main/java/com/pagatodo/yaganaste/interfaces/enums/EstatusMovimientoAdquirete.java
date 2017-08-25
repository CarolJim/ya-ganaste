package com.pagatodo.yaganaste.interfaces.enums;

import android.support.annotation.DrawableRes;

import com.pagatodo.yaganaste.R;

/**
 * Created by Jordan on 25/08/2017.
 */

public enum EstatusMovimientoAdquirete {

    SIN_ESTATUS("0", "Error Estatus", R.color.yellow),
    CACELADA("1", "Cancelada", R.color.redColorNegativeMovements),
    POR_REEMBOLSAR("2", " Por Reembolsar", R.color.colorAccent),
    REEMBOLSADA("3", "Reembolsada", R.color.greenColorPositiveMovements);

    private String id;
    private String description;
    private int color;

    EstatusMovimientoAdquirete(String id, String descripcion, @DrawableRes int color) {
        this.id = id;
        this.description = descripcion;
        this.color = color;
    }

    public static EstatusMovimientoAdquirete getEstatusById(String id) {
        for (EstatusMovimientoAdquirete estatus : values()) {
            if (estatus.getId().equals(id)) {
                return estatus;
            }
        }
        return SIN_ESTATUS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
