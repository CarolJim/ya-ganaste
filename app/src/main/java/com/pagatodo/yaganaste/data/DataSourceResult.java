package com.pagatodo.yaganaste.data;

import com.pagatodo.yaganaste.interfaces.enums.DataSource;

/**
 * Created by flima on 20/02/2017.

 * @author flima
 * @version 1.0
 *
 * Clase para representar el resultado de una petición a cualquier data source.
 *
 */

public class DataSourceResult {

    protected DataSource typeDataSource;
    protected Object data;


    public DataSourceResult(DataSource typeDataSource, Object data) {
        this.typeDataSource = typeDataSource;
        this.data = data;
    }

    public DataSource getTypeDataSource() {
        return typeDataSource;
    }

    public void setTypeDataSource(DataSource typeDataSource) {
        this.typeDataSource = typeDataSource;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
