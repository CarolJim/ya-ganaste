package com.pagatodo.yaganaste.data.local.persistence.db;


import com.pagatodo.yaganaste.data.local.persistence.db.contract.DBContract;
import com.pagatodo.yaganaste.data.local.persistence.db.utils.FieldName;

/**
 * @author Juan Guerra on 10/01/2017.
 */

public class AbstractEntity {

    public static final int INSERTED = 1;
    public static final int DELETED = 2;

    @FieldName(value = DBContract.DBTable.DB_STATE)
    protected int dbState;

    public int getDbState() {
        return dbState;
    }

    public void setDbState(int dbState) {
        this.dbState = dbState;
    }
}
