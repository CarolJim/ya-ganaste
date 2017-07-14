package com.pagatodo.yaganaste.net;

import com.pagatodo.yaganaste.data.DataSourceResult;

/**
 * Created on 16/02/2017.
 *
 * @author flima
 * @version 1.0
 *          <p>
 *          Clase para obtener el resultado de una petición de obtención de datos.
 */
public interface IRequestResult<Data extends DataSourceResult> {

    void onSuccess(Data data);

    void onFailed(Data error);
}
