package com.pagatodo.yaganaste.data.room_db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Jordan on 24/04/2017.
 */

@Entity
public class MontoComercio {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_monto_comercio")
    public int idMontoComercio;

    @ColumnInfo(name = "id_comercio")
    public int idComercio;

    private Double monto;

    public MontoComercio() {
    }

    public int getIdMontoComercio() {
        return idMontoComercio;
    }

    public void setIdMontoComercio(int idMontoComercio) {
        this.idMontoComercio = idMontoComercio;
    }

    public int getIdComercio() {
        return idComercio;
    }

    public void setIdComercio(int idComercio) {
        this.idComercio = idComercio;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }
}
