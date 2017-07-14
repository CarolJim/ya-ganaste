package com.pagatodo.yaganaste.data.model.db;

import com.pagatodo.yaganaste.data.local.persistence.db.AbstractEntity;
import com.pagatodo.yaganaste.data.local.persistence.db.utils.FieldName;
import com.pagatodo.yaganaste.data.local.persistence.db.utils.TableName;

import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.MontosComercio.ID_COMERCIO;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.MontosComercio.ID_MONTO_COMERCIO;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.MontosComercio.MONTO;
import static com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract.MontosComercio.TABLE;

/**
 * Created by Jordan on 24/04/2017.
 */

@TableName(TABLE)
public class MontoComercio extends AbstractEntity {

    @FieldName(value = ID_MONTO_COMERCIO, autoIncrement = true, primaryKey = true)
    private int idMonto;

    @FieldName(ID_COMERCIO)
    private int idComercio;

    @FieldName(MONTO)
    private Double monto;

    public MontoComercio() {
    }

    public int getIdMonto() {
        return idMonto;
    }

    public void setIdMonto(int idMonto) {
        this.idMonto = idMonto;
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
