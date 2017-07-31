package com.pagatodo.yaganaste.data.local.persistence.db;


import android.os.Parcelable;

import com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract;
import com.pagatodo.yaganaste.data.local.persistence.db.utils.FieldName;

import java.io.Serializable;

/**
 * @author Juan Guerra on 10/01/2017.
 */

public class AbstractEntity implements Serializable {


    @FieldName(value = DBContract.DBTable.DB_STATE)
    protected int dbState;

    public int getDbState() {
        return dbState;
    }

    public void setDbState(int dbState) {
        this.dbState = dbState;
    }
}
