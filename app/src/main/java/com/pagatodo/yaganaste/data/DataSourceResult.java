package com.pagatodo.yaganaste.data;

import com.pagatodo.yaganaste.interfaces.enums.DataSource;
import com.pagatodo.yaganaste.interfaces.enums.WebService;

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
    private WebService webService;


    public DataSourceResult(WebService method, DataSource typeDataSource, Object data){
        this.typeDataSource = typeDataSource;
        this.data = data;
        this.webService = method;
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

    public WebService getWebService() {
        return webService;
    }

    public void setWebService(WebService webService) {
        this.webService = webService;
    }
}
